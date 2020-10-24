(function() {

    return {

        init: function() {
            this.menuPrincipalLayout();
            this.bindBtnsMenu();
            this.bindBtnsAcoes(this);
            this.bindBotoesModais();
            this.bindBotoesSubmeterModais();
            //this.tableDataTable();
            this.loadCharts();

            //Inicia o dashboard
            $('aside div.menu a').eq(1).trigger('click');

            //$('#icheff-receitas button').eq(0).trigger('click');
        },

        //Primeira letra maiúscula
        primeiraMaiuscula: (s) => {
            if(typeof s !== 'string')
                return ''
            return s.charAt(0).toUpperCase() + s.slice(1)
        },

        //Botões menu
        bindBtnsMenu: function(){

            //Bind em todos os links
            $('aside div.menu a').on('click', (event) => {
                event.preventDefault();
                
                let secao = $(event.target).attr('href').replace('#','');
                let _this = this;
                
                $('div.admin-content:visible').slideUp(200, () => {
                    if(!_this)
                        return;

                    //Async/Await
                    _this['consultaLista' + _this.primeiraMaiuscula(secao)]();

                    $('#icheff-' + secao).delay(50).slideDown(200);

                    _this = null;
                });

            });

            //Botão de sair
            $('aside button.btn-sair').on('click', function() {
                alert('Sair!');
            });
        },

        //Função limpar todas os inputs/selects
        limparForm: function($form){
            $form.find('input').val('');
            $form.find('select option:selected').removeAttr('selected');
        },

        //Botões de ação
        bindBtnsAcoes: (_this) => {

            //Edit
            $('main').on('click', 'img.icon-edit', (event) => {
                let item_id = $(event.target).attr('item_id');
                let $modalEdit = $(event.target).parents('.admin-content').find('.modal');
                let $form = $modalEdit.find('form');
                let secao = $(event.target).parents('div.admin-content').attr('id').replace('icheff-','');
                let json = JSON.parse($(event.target).attr('data'));

                $modalEdit.find('h5').html('Editar ' + secao);
                
                //Async/Await
                _this['preencheEditModal' + _this.primeiraMaiuscula(secao)]($form, json);

                let $btn = $modalEdit.find('.modal-footer button');
                $btn.html('Salvar')
                $btn.attr('acao', secao + '/editar/' + item_id);

                $modalEdit.modal();
            });

            //Delete
            $('main').on('click','img.icon-delete', function(){
                let item_id = $(this).attr('item_id');
                let $modalDelete = $('#modalDelete');
                let $btnDelete = $modalDelete.find('button.btn-delete');
                let secao = $(this).parents('div.admin-content').attr('id').replace('icheff-','');

                $btnDelete.attr('item_id', item_id);
                $btnDelete.attr('secao', secao);

                $modalDelete.find('h5 span').html(secao);
                $modalDelete.find('.modal-body span').html(item_id);
                
                $modalDelete.modal();
            });

            //Botão confirmar o delete
            $('button.btn-delete').on('click', function(){
                let item_id = $(this).attr('item_id');
                let secao = $(this).attr('secao');

                //ichef.com.br/api/{secao}/{id}

                let url = 'ichef.com.br/api/' + secao + '/' + item_id;

                console.log(url);

                //Ajax

            });
        },

        //Preencher campos do modal
        preencheEditModalIngredientes: function($form, json){
            $form.find('input#ingrediente-nome').val(json.ing_nome);

            $form.find('select#ingrediente-unidade option:selected').removeAttr('selected');
            $form.find('select#ingrediente-unidade option[value="' + json.ing_unidade_padrao + '"]').attr('selected','');

            $form.find('input#ingrediente-custo').val(json.ing_custo.toString().replace('.',','));

            $form.find('select#ingrediente-ativo option:selected').removeAttr('selected');
            $form.find('select#ingrediente-ativo option[value="' + json.ing_ativo + '"]').attr('selected','');
        },

        preencheEditModalCategorias: function($form, $json){
            
        },

        preencheEditModalReceitas: function($form, $json){
            
        },

        //Consultas
        consultaListaDashboard: function(){
            console.log('Consulta Dashboard');
            return {};
        },

        consultaListaIngredientes: function(){
            
            //ing_unidade_padrao
            //ichef.com.br/api/listaingredientes
            let respostaAjax = {
                sucesso: 1,
                listaIngredientes: [
                    {
                        ing_id: 1,
                        ing_nome: 'Açúcar',
                        iun_unidade_sigla: 'kg',
                        ing_unidade_padrao: 2,
                        ing_custo: 3.2,
                        ing_ativo: 1
                    },
                    {
                        ing_id: 2,
                        ing_nome: 'Farinha',
                        iun_unidade_sigla: 'kg',
                        ing_unidade_padrao: 2,
                        ing_custo: 3,
                        ing_ativo: 1
                    },
                    {
                        ing_id: 3,
                        ing_nome: 'Macarrão',
                        iun_unidade_sigla: 'g',
                        ing_unidade_padrao: 1,
                        ing_custo: 2.55,
                        ing_ativo: 1
                    }
                ]
            }
            
            let data = respostaAjax;
            //Ajax success:
            if(data.sucesso==1){
                
                //Carregar os dados na tela
                
                let $tabelaBody = $('div#icheff-ingredientes table tbody');

                $tabelaBody.html('');

                for(let i in data.listaIngredientes){

                    let $edit = $('<img class="icon-edit" src="img/icons/icon-edit.png">');
                    let $delete = $('<img class="icon-delete" src="img/icons/icon-delete.png">');

                    let $ing = data.listaIngredientes[i];

                    $edit.attr('data', JSON.stringify($ing));

                    let $tr = $('<tr>');
                    let $tdOpcoes = $('<td>');

                    $tr.append('<td>' + $ing['ing_id'] + '</td>');
                    $tr.append('<td>' + $ing['ing_nome'] + '</td>');
                    $tr.append('<td>' + $ing['iun_unidade_sigla'] + '</td>');
                    $tr.append('<td>' + $ing['ing_custo'] + '</td>');
                    $tr.append('<td>' + $ing['ing_ativo'] + '</td>');

                    $edit.attr('item_id', $ing['ing_id']);
                    $tdOpcoes.append($edit)

                    $delete.attr('item_id', $ing['ing_id']);
                    $tdOpcoes.append($delete)

                    $tr.append($tdOpcoes);

                    $tabelaBody.append($tr);
                
                }

                /*
                $('div#icheff-ingredientes table').DataTable().destroy(true);

                $('div#icheff-ingredientes table').DataTable({
                    "paging": false,
                    "info": false,
                    "columnDefs": [
                        //className: "text-center"
                        //"orderable": false
                        { "targets": 0, "width": "50px", className: "text-center" },
                        { "targets": 1 },
                        { "targets": 2 },
                        { "targets": 3 },
                        { "targets": 4 },
                        { "targets": -1, className: "text-center" }
                    ]
                });
                */

            } else {
                alert('Deu erro no ajax!')
            }            

        },

        consultaListaCategorias: function(){
            console.log('Consulta Categorias');
            return {};
        },

        consultaListaReceitas: function(){
            console.log('Consulta Receitas');
            return {};
        },

        consultaListaUsuarios: function(){
            console.log('Consulta Usuarios');
            return {};
        },

        //Botões modais
        bindBotoesModais: function(){

            let _this = this;

            $('.card-admin-content').on('click', 'div.btn-novo button', function() {
                let $modal = $(this).parents('.card-admin-content').find('.modal');
                let $modalButton = $modal.find('.modal-footer button');
                let secaoPlural = $(this).parents('div.admin-content').attr('id').replace('icheff-','');
                let secao = secaoPlural.substring(0, secaoPlural.length - 1);

                let novoTxt = (secao=='ingrediente'?'Novo':'Nova');

                _this.limparForm($modal.find('form'));

                $modal.find('h5').html(novoTxt + ' ' + secao);
                $modalButton.html('Criar');
                $modalButton.removeAttr('item_id');
                $modalButton.attr('acao', secaoPlural + '/cadastrar');

                $modal.modal();
            });
        },

        bindBotoesSubmeterModais: function(){
            $('.card-admin-content').on('click', 'div.modal-footer button', (event) => {
                
                let $divContent = $(event.target).parents('div.admin-content');
                let secao = $divContent.attr('id').replace('icheff-','');
                let $form = $divContent.find('div.modal form');
                let _this = this;

                //Async/Await
                _this['submeterModal' + _this.primeiraMaiuscula(secao)]($form);

            });
        },

        submeterModalIngredientes: function($form){

            //ichef.com.br/api/ingredientes/cadastar
            //ichef.com.br/api/ingredientes/editar/{id}
            let formData = {
                ing_nome: $form.find('input#ingrediente-nome').val(),
                ing_unidade_padrao: $form.find('input#ingrediente-quantidade').val(),
                ing_custo: $form.find('select#ingrediente-unidade option:selected').val(),
                ing_ativo: $form.find('input#ingrediente-custo').val()
            }

            //Ajax

            console.log(formData);

        },

        submeterModalCategorias: function($form){
            console.log('Categorias');
        },

        submeterModalReceitas: function($form){
            console.log('Receitas');
        },

        tableDataTable: function(){
            $('table:not(.table-ingredientes)').DataTable({
                "paging": false,
                "info": false,
                "columnDefs": [
                    //className: "text-center"
                    //"orderable": false
                    { "targets": 0, "width": "50px", className: "text-center" },
                    { "targets": 1 },
                    { "targets": 2 },
                    { "targets": 3 },
                    { "targets": 4 },
                    { "targets": -1, className: "text-center" }
                ]
            });
        },

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
        },

        loadCharts: function(){
            google.charts.load('current', {packages: ['corechart']});
            google.charts.setOnLoadCallback(drawChart);
            
            function drawChart() {
    
                var data = google.visualization.arrayToDataTable([
                    ['Ação', 'Quantidade'],
                    ['Variados', 55],
                    ['Peixes e frutos do mar', 20],
                    ['Fitness', 50],
                    ['Vegetarianos', 20]
                ]);
                
                var options = {
                    title: 'Distribuição de vendas por categoria',
                    is3D: true,
                    chartArea: {width: '93%', height: '80%', left: '0'}
                };
                
                var chart = new google.visualization.PieChart(document.getElementById('atividades-chart'));
                
                chart.draw(data, options);
            }

            google.charts.setOnLoadCallback(drawChart2);
      
            function drawChart2() {
      
              var data = new google.visualization.DataTable();
              data.addColumn('date', 'Dia');
              data.addColumn('number', 'Quantidade de vendas');
      
              data.addRows([
                [new Date(2020, 0, 1), 5],  [new Date(2020, 0, 2), 7],  [new Date(2020, 0, 3), 3],
                [new Date(2020, 0, 4), 1],  [new Date(2020, 0, 5), 3],  [new Date(2020, 0, 6), 4],
                [new Date(2020, 0, 7), 3],  [new Date(2020, 0, 8), 4],  [new Date(2020, 0, 9), 2],
                [new Date(2020, 0, 10), 5], [new Date(2020, 0, 11), 8], [new Date(2020, 0, 12), 6],
                [new Date(2020, 0, 13), 3], [new Date(2020, 0, 14), 3], [new Date(2020, 0, 15), 5],
                [new Date(2020, 0, 16), 7], [new Date(2020, 0, 17), 6], [new Date(2020, 0, 18), 6],
                [new Date(2020, 0, 19), 3], [new Date(2020, 0, 20), 1], [new Date(2020, 0, 21), 2],
                [new Date(2020, 0, 22), 4], [new Date(2020, 0, 23), 6], [new Date(2020, 0, 24), 5],
                [new Date(2020, 0, 25), 9], [new Date(2020, 0, 26), 4], [new Date(2020, 0, 27), 9],
                [new Date(2020, 0, 28), 8], [new Date(2020, 0, 29), 6], [new Date(2020, 0, 30), 4],
                [new Date(2020, 0, 31), 6], [new Date(2020, 1, 1), 7],  [new Date(2020, 1, 2), 9]
              ]);
      
              var options = {
                title: 'Histórico de vendas',
                legend: 'none',
                hAxis: {
                  format: 'd/M/yyyy',
                  gridlines: {count: 15}
                },
                vAxis: {
                  //gridlines: {color: 'none'},
                  minValue: 0
                },
                chartArea: {width: '90%', height: '80%'},
              };
      
              var chart = new google.visualization.LineChart(document.getElementById('historico-chart'));
      
              chart.draw(data, options);

            }
        }

    }

})().init();