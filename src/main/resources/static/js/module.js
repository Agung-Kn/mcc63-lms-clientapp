/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let table = null;
let moduleId = null;

$(document).ready(() => {
    getAllModule();
    getAllCourse();
    onSubmitModule();
});

const getAllModule = () => {
    table = $('#module-table').DataTable({
        ajax: {
            url: '/module/all',
            dataSrc: ''
        },
        destroy: true,
        columns: [
            {data: 'id'},
            {data: 'title'},
            {data: 'course.title'},
            {
                sortable: false,
                render: function (i, row, module) {
                    return `<button class="btn btn-sm btn-info me-2" onclick='getModuleDetail(${JSON.stringify(module)})'>Detail</button>
                                <button class="btn btn-sm btn-warning me-2" onclick='onUpdateModalShow(${JSON.stringify(module)})'>Update</button>
                                <button type="submit" class="btn btn-sm btn-danger" onclick="onDeleteModule(${module.id})">Delete</button>`;
                }
            }
        ]
    });
};

const getAllCourse = () => {
    $.ajax({
        url: '/course/all',
        method: 'GET',
        success: (res) => {
            $.each(res, (i, course) => {
                $('#courseId').append($(`<option value="${course.id}">${course.title}</option>`));
            });
        },
        error: (err) => {
            console.log(err);
        }
    });
};


const getModuleDetail = (module) => {
    $('#module-modal-form').modal('show');

    $('#title').val(module.title);
    $('#courseId').val(module.course.id);

    formProp(true);

    $('#module-modal-form button[type=submit]').addClass('d-none');
};

const onSubmitModule = () => {
    $('#module-form').on('submit', (e) => {
        e.preventDefault();

        const data = {
            title: $('#title').val(),
            courseId: $('#courseId').val()
        };

        if (this.moduleId) {
            onUpdateModule(data);
        } else {
            onCreateModule(data);
        }
    });
};

const onCreateModule = (data) => {
    $.ajax({
        url: '/module/new',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: (res) => {
            toast().fire({
                icon: 'success',
                title: res.message
            });

            $('#module-modal-form').modal('hide');
            getAllModule();
        },
        error: (err) => {
            toast().fire({
                icon: 'success',
                title: err.message
            });
        }
    });
};

const onUpdateModule = (data) => {
    $.ajax({
        url: '/module/edit/' + this.moduleId,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: (res) => {
            toast().fire({
                icon: 'success',
                title: res.message
            });

            $('#module-modal-form').modal('hide');
            getAllModule();
        },
        error: (err) => {
            toast().fire({
                icon: 'success',
                title: 'Module failed to updated.'
            });
        }
    });
};

const onUpdateModalShow = (module) => {
    $('#module-modal-form').modal('show');

    $('#title').val(module.title);
    $('#courseId').val(module.course.id);

    formProp(false);
    $('#module-modal-form button[type=submit]').removeClass('d-none');

    setModuleId(module.id);
};

const onDeleteModule = (id) => {
    Swal.fire({
        title: 'Are you sure?',
        text: "do you want to delete this data?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Delete'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: '/module/delete/' + id,
                type: 'DELETE',
                success: (res) => {
                    toast().fire({
                        icon: 'success',
                        title: res.message
                    });
                    getAllModule();
                },
                error: (err) => {
                    toast().fire({
                        icon: 'error',
                        title: err.message
                    });
                }
            });
        }
    });
};

const formProp = (data) => {
    $('#module-modal-form input').prop('disabled', data);
    $('#module-modal-form select').prop('disabled', data);
};

const setModuleId = (id) => {
    this.moduleId = id;

    if (!id) {
        $('#title').val('');
        $('#courseId').val('');

        $('#module-modal-form button[type=submit]').removeClass('d-none');
    }
};

const toast = () => {
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer);
            toast.addEventListener('mouseleave', Swal.resumeTimer);
        }
    });

    return Toast;
};