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
});


const getAllCourse = () => {
    table = $('#course-table').DataTable({
            ajax: {
                url: '/course/all',
                dataSrc: ''
            },
            columns: [
                {data: 'id'},
                {data: 'title'},
                {data: 'price'},
                {data: 'category.name'},
                {
                    sortable: false,
                    render: function (i, row, course) {
                        return `<button class="btn btn-sm btn-info me-2" onclick='getCountryDetail(${JSON.stringify(course)})'>Detail</button>
                                <button class="btn btn-sm btn-warning me-2" onclick='onUpdateModalShow(${JSON.stringify(course)})'>Update</button>
                                <button type="submit" class="btn btn-sm btn-danger" onclick="onDeleteCountry(${course.id})">Delete</button>`;
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

const setCourseId = (id) => {
    this.courseId = id;

    if (!id) {
        $('#title').val('');
        $('#description').val('');
        $('#prices').val('');
        $('#categoryId').val('');

        $('#country-modal-form button[type=submit]').removeClass('d-none');
    }
};