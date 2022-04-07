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
});


const getAllContent = () => {
    table = $('#content-table').DataTable({
            ajax: {
                url: '/content/all',
                dataSrc: ''
            },
            columns: [
                {data: 'id'},
                {data: 'title'},
                {data: 'price'},
                {data: 'module.name'},
                {
                    sortable: false,
                    render: function (i, row, content) {
                        return `<button class="btn btn-sm btn-info me-2" onclick='getCountryDetail(${JSON.stringify(content)})'>Detail</button>
                                <button class="btn btn-sm btn-warning me-2" onclick='onUpdateModalShow(${JSON.stringify(content)})'>Update</button>
                                <button type="submit" class="btn btn-sm btn-danger" onclick="onDeleteCountry(${content.id})">Delete</button>`;
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
                $('#moduleId').append($(`<option value="${module.id}">${module.name}</option>`));
            });
        },
        error: (err) => {
            console.log(err);
        }
    });
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