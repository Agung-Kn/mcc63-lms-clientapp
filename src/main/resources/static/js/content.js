/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let table = null;
let contentId = null;

$(document).ready(() => {
    getAllContent();
    getAllModule();
    onSubmitContent();
});


const getAllContent = () => {
    table = $('#content-table').DataTable({
        ajax: {
            url: '/content/all',
            dataSrc: ''
        },
        destroy: true,
        columns: [
            {data: 'id'},
            {data: 'title'},
            {data: 'description'},
            {data: 'module.title'},
            {
                sortable: false,
                render: function (i, row, content) {
                    return `<button class="btn btn-sm btn-info me-2" onclick='getContentDetail(${JSON.stringify(content)})'>Detail</button>
                                <button class="btn btn-sm btn-warning me-2" onclick='onUpdateModalShow(${JSON.stringify(content)})'>Update</button>
                                <button type="submit" class="btn btn-sm btn-danger" onclick="onDeleteContent(${content.id})">Delete</button>`;
                }
            }
        ]
    });
};

const getAllModule = () => {
    $.ajax({
        url: '/module/all',
        method: 'GET',
        success: (res) => {
            $.each(res, (i, module) => {
                $('#moduleId').append($(`<option value="${module.id}">${module.title}</option>`));
            });
        },
        error: (err) => {
            console.log(err);
        }
    });
};


const getContentDetail = (content) => {
    $('#content-modal-form').modal('show');

    $('#title').val(content.title);
    $('#description').val(content.description);
    $('#moduleId').val(content.module.id);

    formProp(true);

    $('#content-modal-form button[type=submit]').addClass('d-none');
};

const onSubmitContent = () => {
    $('#content-form').on('submit', (e) => {
        e.preventDefault();

        const data = {
            title: $('#title').val(),
            description: $('#description').val(),
            moduleId: $('#moduleId').val(),
            file: $('#file').val()
        };

        var form = $("#content-form");
        var formData = new FormData(form[0]);

        if (this.contentId) {
            onUpdateContent(formData);
        } else {
            onCreateContent(formData);
        }
    });
};

const onCreateContent = (data) => {
    $.ajax({
        url: '/content/new',
        method: 'POST',
//        data: JSON.stringify(data),
        data: data,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: (res) => {
            toast().fire({
                icon: 'success',
                title: res.message
            });

            $('#content-modal-form').modal('hide');
            getAllContent();
        },
        error: (err) => {
            toast().fire({
                icon: 'success',
                title: err.message
            });
        }
    });
};

const onUpdateContent = (data) => {
    $.ajax({
        url: '/content/edit/' + this.contentId,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: (res) => {
            toast().fire({
                icon: 'success',
                title: res.message
            });

            $('#content-modal-form').modal('hide');
            getAllContent();
        },
        error: (err) => {
            toast().fire({
                icon: 'success',
                title: 'Content failed to updated.'
            });
        }
    });
};

const onUpdateModalShow = (content) => {
    $('#content-modal-form').modal('show');

    $('#title').val(content.title);
    $('#description').val(content.description);
    $('#moduleId').val(content.module.id);

    formProp(false);
    $('#content-modal-form button[type=submit]').removeClass('d-none');

    setContentId(content.id);
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
                url: '/content/delete/' + id,
                type: 'DELETE',
                success: (res) => {
                    toast().fire({
                        icon: 'success',
                        title: res.message
                    });
                    getAllContent();
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
    $('#content-modal-form input').prop('disabled', data);
    $('#content-modal-form select').prop('disabled', data);
};

const setContentId = (id) => {
    this.contentId = id;

    if (!id) {
        $('#title').val('');
        $('#description').val('');
        $('#moduleId').val('');

        $('#content-modal-form button[type=submit]').removeClass('d-none');
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