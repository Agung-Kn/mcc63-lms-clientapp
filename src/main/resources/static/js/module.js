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
});


const getAllModule = () => {
    table = $('#module-table').DataTable({
            ajax: {
                url: '/module/all',
                dataSrc: ''
            },
            columns: [
                {data: 'id'},
                {data: 'title'},
                {data: 'course.title'},
                {
                    sortable: false,
                    render: function (i, row, module) {
                        return `<button class="btn btn-sm btn-info me-2" onclick='getCountryDetail(${JSON.stringify(module)})'>Detail</button>
                                <button class="btn btn-sm btn-warning me-2" onclick='onUpdateModalShow(${JSON.stringify(module)})'>Update</button>
                                <button type="submit" class="btn btn-sm btn-danger" onclick="onDeleteCountry(${module.id})">Delete</button>`;
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

const setModuleId = (id) => {
    this.moduleId = id;

    if (!id) {
        $('#title').val('');
        $('#courseId').val('');

        $('#module-modal-form button[type=submit]').removeClass('d-none');
    }
};