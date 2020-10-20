(function() {

    return {

        init: function() {
            this.menuPrincipalLayout();
            this.bindBtnsMenu();
            this.bindBtnsAcoes();
            this.bindBotoesModais();
            //$('#modalEditIngrediente').modal();
        },

        //Botões menu
        bindBtnsMenu: function(){

            //Bind em todos os links
            $('aside div.menu a').on('click', function(event) {
                event.preventDefault();
                
                let href = $(this).attr('href').replace('#','');
                alert('Ir para: ' + href);

            });

            //Botão de sair
            $('aside button.btn-sair').on('click', function() {
                alert('Sair!');
            });
        },

        //Botões de ação
        bindBtnsAcoes: function(){
            
            //Edit
            $('img.icon-delete').on('click', function() {
                //let db_id = $(this).attr('db_id');
                $('#modalDelete').modal();
            });

            //Delete
            $('img.icon-delete').on('click', function() {
                //let db_id = $(this).attr('db_id');
                $('#modalDelete').modal();
            });

        },

        //Botões modais
        bindBotoesModais: function(){
            $('button#btn-edit-ingrediente').on('click', function() {
                $('#modalEditIngrediente').modal();
            });
        },

        /*
        $('div.admin-content table').DataTable({
            "paging": false,
            "info": false,
            "columnDefs": [
                { "targets": 0, "width": "50px", className: "text-center" },
                { "targets": 1, "width": "50px" },
                { "targets": 2, "orderable": false, className: "text-center" },
                { "targets": 3, "orderable": false },
                { "targets": 4, "orderable": false, className: "text-center" },
                { "targets": 5, "orderable": false, className: "text-center" }
            ]
        });
        */

        //Redimensiona o tamanho do menu
        menuPrincipalLayout: function() {
            let redimensionaObj = function(nome) {
                let $obj = $(nome);
                let totalPadding = parseInt($obj.css('padding-top')) + parseInt($obj.css('padding-bottom'));
                $obj.height($(window).height() - totalPadding);
            }

            let redimensionaMenu = function() {
                redimensionaObj('aside');
                redimensionaObj('main');
            }

            $(document).ready(redimensionaMenu);
            $(window).resize(redimensionaMenu);
        }

    }

})().init();