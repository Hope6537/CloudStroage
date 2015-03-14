var Datatable = function() {

    var tableOptions; // main options
    var dataTable; // datatable object
    var table; // actual table jquery object
    var tableContainer; // actual table container object
    var tableWrapper; // actual table wrapper jquery object
    var tableInitialized = false;
    var the;

    var countSelectedRecords = function() {
        var selected = $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).size();
        var text = tableOptions.dataTable.language.metronicGroupActions;
        if (selected > 0) {
            $('.table-group-actions > span', tableWrapper).text(text.replace("_TOTAL_", selected));
        } else {
            $('.table-group-actions > span', tableWrapper).text("");
        }
    };

    var reloadTable = function (pageFlag) {
        dataTable.draw(pageFlag)
    };

    return {

        //main function to initiate the module
        init: function(options) {

            if (!$().dataTable) {
                return;
            }

            the = this;
            // default settings
            options = $.extend(true, {
                src: "", // actual table  
                filterApplyAction: "filter",
                filterCancelAction: "filter_cancel",
                loadingMessage: '载入中...',
                onQuery : function(data){},
                onSortColumn : function(sortColumn, sortDirection) {
                    return customGlobal.onSortColumnDefault(sortColumn, sortDirection);
                },
                dataTable: {
                    "dom": "<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r><'table-scrollable't><'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>", // datatable layout
                    "lengthMenu": [ 5,10, 25, 50, 75, 100 ],
                    search:true,
                    "pageLength": 10, // default records per page
                    "language": { // language settings
                        // metronic spesific
                        "metronicGroupActions": "共 _TOTAL_ 条记录被选中",
                        "metronicAjaxRequestGeneralError": "请求失败，请检查网络连接！",

                        // data tables spesific
                        "lengthMenu": "<span class='seperator'>|</span>每页 _MENU_ 条",
                        "info": "<span class='seperator'>|</span>共 _TOTAL_ 条",
                        "infoEmpty": "未查询到记录",
                        "emptyTable": "表格中无可用数据",
                        "zeroRecords": "未找到匹配记录",
                        "paginate": {
                            "previous": "上一页",
                            "next": "上一页",
                            "last": "最末页",
                            "first": "第一页",
                            "page": "第",
                            "pageOf": "页，共"
                        }
                    },
                    "columnDefs": [{  // set default column settings
                        'orderable': false,
                        'targets': [0]
                    }, {
                        "searchable": false,
                        "targets": [0]
                    }],
                    "orderCellsTop": true,
                    "pagingType": "bootstrap_extended", // pagination type(bootstrap, bootstrap_full_number or bootstrap_extended)
                    "autoWidth": true, // disable fixed width and enable fluid table
                    "processing": false, // enable/disable display message box on record load
                    "serverSide": true, // enable/disable server side ajax loading
                    "ajax": { // define ajax settings
                        "url": "", // ajax URL
                        "type": "POST", // request type
                        "timeout": 20000,
                        "data": function(data) { // add request parameters before submit
                            if(options.onQuery){
                                options.onQuery(data);
                            }
                            if(options.onSortColumn){
                                if (data.order[0]) {
                                    var sortTemp = options.onSortColumn(options.dataTable.columns[data.order[0].column].data, data.order[0].dir);
                                    data.sortField = sortTemp.sortColumn;
                                    data.sortOrder = sortTemp.sortDirection;
                                }else{
                                    data.sortField = "";
                                    data.sortOrder = "";
                                }
                            }
                            data.pageNo=parseInt(data.start/data.length,10)+1;
                            data.pageSize=data.length;
                            Metronic.blockUI({
                                message: tableOptions.loadingMessage,
                                target: tableContainer,
                                overlayColor: 'none',
                                cenrerY: true,
                                boxed: true
                            });
                        },
                        "dataSrc": function(res) { // Manipulate the data returned from the server
                            if ($('.group-checkable', table).size() === 1) {
                                $('.group-checkable', table).attr("checked", false);
                                $.uniform.update($('.group-checkable', table));
                            }

                            if (tableOptions.onSuccess) {
                                tableOptions.onSuccess.call(undefined, the);
                            }

                            Metronic.unblockUI(tableContainer);

                            return res.data;
                        },
                        "error": function() { // handle general connection errors
                            if (tableOptions.onError) {
                                tableOptions.onError.call(undefined, the);
                            }

                            Metronic.alert({
                                type: 'danger',
                                icon: 'warning',
                                message: tableOptions.dataTable.language.metronicAjaxRequestGeneralError,
                                container: tableWrapper,
                                place: 'prepend'
                            });

                            Metronic.unblockUI(tableContainer);
                        }
                    },

                    "drawCallback": function(oSettings) { // run some code on table redraw
                        if (tableInitialized === false) { // check if table has been initialized
                            tableInitialized = true; // set table initialized
                            table.show(); // display table
                        }
                        Metronic.initUniform($('input[type="checkbox"]', table)); // reinitialize uniform checkboxes on each table reload
                        countSelectedRecords(); // reset selected records indicator
                    }
                }
            }, options);

            tableOptions = options;

            // create table's jquery object
            table = $(options.src);
            tableContainer = table.parents(".table-container");

            // apply the special class that used to restyle the default datatable
            var tmp = $.fn.dataTableExt.oStdClasses;

            $.fn.dataTableExt.oStdClasses.sWrapper = $.fn.dataTableExt.oStdClasses.sWrapper + " dataTables_extended_wrapper";
            $.fn.dataTableExt.oStdClasses.sFilterInput = "form-control input-small input-sm input-inline";
            $.fn.dataTableExt.oStdClasses.sLengthSelect = "form-control input-xsmall input-sm input-inline";

            // initialize a datatable
            dataTable = table.DataTable(options.dataTable);

            // revert back to default
            $.fn.dataTableExt.oStdClasses.sWrapper = tmp.sWrapper;
            $.fn.dataTableExt.oStdClasses.sFilterInput = tmp.sFilterInput;
            $.fn.dataTableExt.oStdClasses.sLengthSelect = tmp.sLengthSelect;

            // get table wrapper
            tableWrapper = table.parents('.dataTables_wrapper');

            // build table group actions panel
            if ($('.table-actions-wrapper', tableContainer).size() === 1) {
                $('.table-group-actions', tableWrapper).html($('.table-actions-wrapper', tableContainer).html()); // place the panel inside the wrapper
                $('.table-actions-wrapper', tableContainer).remove(); // remove the template container
            }
            // handle group checkboxes check/uncheck
            $('.group-checkable', table).change(function() {
                var set = $('tbody > tr > td:nth-child(1) input[type="checkbox"]', table);
                var checked = $(this).is(":checked");
                $(set).each(function() {
                    $(this).attr("checked", checked);
                });
                $.uniform.update(set);
                countSelectedRecords();
            });

            // handle row's checkbox click
            table.on('change', 'tbody > tr > td:nth-child(1) input[type="checkbox"]', function() {
                countSelectedRecords();
            });

            // handle filter submit button click
            table.on('click', '.filter-submit', function(e) {
                e.preventDefault();
                dataTable.ajax.reload();
            });

            // handle filter cancel button click
            table.on('click', '.filter-cancel', function(e) {
                e.preventDefault();
                $('textarea.form-filter, select.form-filter, input.form-filter', table).each(function() {
                    $(this).val("");
                });
                $('input.form-filter[type="checkbox"]', table).each(function() {
                    $(this).attr("checked", false);
                });
                dataTable.ajax.reload();
            });

            var tableId = tableOptions.src.attr("id");
            //显示列选择按钮事件
            $('#'+tableId+'ToggleColumn').find('input[type="checkbox"]').on("change",function () {
                var iCol = parseInt($(this).attr("data-column"));
                dataTable.column(iCol).visible(!dataTable.column(iCol).visible());
            });

            $("#"+tableId+"Reload").on("click",function(){
                reloadTable(false);
            })
        },

        getSelectedRowsCount: function() {
            return $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).size();
        },

        getSelectedRows: function() {
            var rows = [];
            $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).each(function() {
                rows.push($(this).val());
            });

            return rows;
        },

        getDataTable: function() {
            return dataTable;
        },

        getTableWrapper: function() {
            return tableWrapper;
        },

        gettableContainer: function() {
            return tableContainer;
        },

        getTable: function() {
            return table;
        },

        reloadTable: reloadTable
    };

};