/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let table = null;
let courseId = null;

$(document).ready(() => {
    getAllCourse();
    getAllCategory();
    onSubmitCourse();
});


const getAllCourse = () => {
    table = $('#course-table').DataTable({
            ajax: {
                url: '/course/all',
                dataSrc: ''
            },
            destroy: true,
            columns: [
                {data: 'id'},
                {data: 'title'},
                {data: 'price'},
                {data: 'category.name'},
                {
                    sortable: false,
                    render: function (i, row, course) {
                        return `<button class="btn btn-sm btn-info me-2" onclick='getCourseDetail(${JSON.stringify(course)})'>Detail</button>
                                <button class="btn btn-sm btn-warning me-2" onclick='onUpdateModalShow(${JSON.stringify(course)})'>Update</button>
                                <button type="submit" class="btn btn-sm btn-danger" onclick="onDeleteCourse(${course.id})">Delete</button>`;
                    }
                }
            ]
        });
};

const getAllCategory = () => {
    $.ajax({
        url: '/category/all',
        method: 'GET',
        success: (res) => {
            $.each(res, (i, category) => {
                $('#categoryId').append($(`<option value="${category.id}">${category.name}</option>`));
            });
        },
        error: (err) => {
            console.log(err);
        }
    });
};

const getCourseDetail = (course) => {
    $('#course-modal-form').modal('show');

    $('#title').val(course.title);
    $('#description').val(course.description);
    $('#price').val(course.price);
    $('#categoryId').val(course.category.id);

    formProp(true);

    $('#course-modal-form button[type=submit]').addClass('d-none');
};

const onSubmitCourse = () => {
    $('#course-form').on('submit', (e) => {
        e.preventDefault();

        const data = {
            title: $('#title').val(),
            description: $('#description').val(),
            price: $('#price').val(),
            userId: $('#userId').val(),
            categoryId: $('#categoryId').val()
        };

        if (this.courseId) {
            onUpdateCourse(data);
        } else {
            onCreateCourse(data);
        }
    });
};

const onCreateCourse = (data) => {
    $.ajax({
        url: '/course/new',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: (res) => {
            toast().fire({
                icon: 'success',
                title: res.message
            });

            $('#course-modal-form').modal('hide');
            getAllCourse();
        },
        error: (err) => {
            toast().fire({
                icon: 'success',
                title: err.message
            });
        }
    });
};

const onUpdateCourse = (data) => {
    $.ajax({
        url: '/course/edit/' + this.courseId,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: (res) => {
            toast().fire({
                icon: 'success',
                title: res.message
            });

            $('#course-modal-form').modal('hide');
            getAllCourse();
        },
        error: (err) => {
            toast().fire({
                icon: 'success',
                title: 'Course failed to updated.'
            });
        }
    });
};

const onUpdateModalShow = (course) => {
    $('#course-modal-form').modal('show');

    $('#title').val(course.title);
    $('#description').val(course.description);
    $('#price').val(course.price);
    $('#categoryId').val(course.category.id);

    formProp(false);
    $('#course-modal-form button[type=submit]').removeClass('d-none');

    setCourseId(course.id);
};

const onDeleteCourse = (id) => {
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
                url: '/course/delete/' + id,
                type: 'DELETE',
                success: (res) => {
                    toast().fire({
                        icon: 'success',
                        title: res.message
                    });
                    getAllCourse();
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
    $('#course-modal-form input').prop('disabled', data);
    $('#course-modal-form select').prop('disabled', data);
};

const setCourseId = (id) => {
    this.courseId = id;

    if (!id) {
        $('#title').val('');
        $('#description').val('');
        $('#prices').val('');
        $('#categoryId').val('');

        formProp(false);
        $('#course-modal-form button[type=submit]').removeClass('d-none');
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