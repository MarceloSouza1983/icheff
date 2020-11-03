(function () {

    return {

        init: function () {
            this.menuPrincipalLayout();
            this.bindBtnsMenu();
            this.bindBtnsAcoes(this);
            this.bindBotoesModais();
            this.bindBotoesSubmeterModais();
            this.bindBotoesInternosModais();

            //Inicia o dashboard
            $('aside div.menu a').eq(3).trigger('click');

            //$('#icheff-receitas button').eq(0).trigger('click');
        },

        //Primeira letra maiúscula
        primeiraMaiuscula: (s) => {
            if (typeof s !== 'string')
                return ''
            return s.charAt(0).toUpperCase() + s.slice(1)
        },

        //Botões menu
        bindBtnsMenu: function () {

            //Bind em todos os links
            $('aside div.menu a').on('click', (event) => {
                event.preventDefault();

                let secao = $(event.target).attr('href').replace('#', '');
                let _this = this;

                $('div.admin-content:visible').slideUp(200, () => {
                    if (!_this)
                        return;

                    //Async/Await
                    _this['consultaLista' + _this.primeiraMaiuscula(secao)]();

                    $('#icheff-' + secao).delay(50).slideDown(200);

                    _this = null;
                });

            });

            //Botão de sair
            $('aside button.btn-sair').on('click', function () {
                alert('Sair!');
            });
        },

        //Função limpar todas os inputs/selects
        limparForm: function ($form) {
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
                let secao = $(event.target).parents('div.admin-content').attr('id').replace('icheff-', '');
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
            $('main').on('click', 'img.icon-delete', function () {
                let item_id = $(this).attr('item_id');
                let $modalDelete = $('#modalDelete');
                let $btnDelete = $modalDelete.find('button.btn-delete');
                let secao = $(this).parents('div.admin-content').attr('id').replace('icheff-', '');

                $btnDelete.attr('item_id', item_id);
                $btnDelete.attr('secao', secao);

                $modalDelete.find('h5 span').html(secao);
                $modalDelete.find('.modal-body span').html(item_id);

                $modalDelete.modal();
            });

            //Botão confirmar o delete
            $('button.btn-delete').on('click', function () {
                let item_id = $(this).attr('item_id');
                let secao = $(this).attr('secao');

                //ichef.com.br/api/{secao}/{id}

                let url = 'ichef.com.br/api/' + secao + '/' + item_id;

                console.log(url);

                //Ajax

            });
        },

        //Preencher campos do modal
        preencheEditModalIngredientes: function ($form, json) {
            $form.find('input#ingrediente-nome').val(json.ing_nome);

            $form.find('select#ingrediente-unidade option:selected').removeAttr('selected');
            $form.find('select#ingrediente-unidade option[value="' + json.ing_unidade_padrao + '"]').attr('selected', '');

            $form.find('input#ingrediente-custo').val(json.ing_custo.toString().replace('.', ','));

            $form.find('select#ingrediente-ativo option:selected').removeAttr('selected');
            $form.find('select#ingrediente-ativo option[value="' + json.ing_ativo + '"]').attr('selected', '');
        },

        preencheEditModalCategorias: function ($form, $json) {

        },

        preencheEditModalReceitas: function ($form, $json) {

        },

        //Consultas
        consultaListaDashboard: function () {

            let respostaAjax = {
                sucesso: 1,
                estatisticas: {
                    total_ingredientes: 44,
                    total_receitas: 30,
                    total_clientes: 202,
                    total_vendas: 1250,
                },
                historico_de_vendas: [
                    { data: '2020/1/1', quantidade: 5 },
                    { data: '2020/1/2', quantidade: 7 },
                    { data: '2020/1/3', quantidade: 3 },
                    { data: '2020/1/4', quantidade: 1 },
                    { data: '2020/1/5', quantidade: 3 },
                    { data: '2020/1/6', quantidade: 4 },
                    { data: '2020/1/7', quantidade: 3 },
                    { data: '2020/1/8', quantidade: 4 },
                    { data: '2020/1/9', quantidade: 2 },
                    { data: '2020/1/10', quantidade: 5 },
                    { data: '2020/1/11', quantidade: 8 },
                    { data: '2020/1/12', quantidade: 6 },
                    { data: '2020/1/13', quantidade: 3 },
                    { data: '2020/1/14', quantidade: 3 },
                    { data: '2020/1/15', quantidade: 5 },
                    { data: '2020/1/16', quantidade: 7 },
                    { data: '2020/1/17', quantidade: 6 },
                    { data: '2020/1/18', quantidade: 6 },
                    { data: '2020/1/19', quantidade: 3 },
                    { data: '2020/1/20', quantidade: 1 },
                    { data: '2020/1/21', quantidade: 2 },
                    { data: '2020/1/22', quantidade: 4 },
                    { data: '2020/1/23', quantidade: 6 },
                    { data: '2020/1/24', quantidade: 5 },
                    { data: '2020/1/25', quantidade: 9 },
                    { data: '2020/1/26', quantidade: 4 },
                    { data: '2020/1/27', quantidade: 9 },
                    { data: '2020/1/28', quantidade: 8 },
                    { data: '2020/1/29', quantidade: 6 },
                    { data: '2020/1/30', quantidade: 4 },
                    { data: '2020/1/31', quantidade: 6 },
                    { data: '2020/2/1', quantidade: 7 },
                    { data: '2020/2/2', quantidade: 9 }
                ],
                distribuicao_de_vendas: [
                    //Ação / Quantidade
                    ['Variados', 55],
                    ['Peixes e frutos do mar', 20],
                    ['Fitness', 50],
                    ['Vegetarianos', 20]
                ]
            };

            let data = respostaAjax;
            //Ajax success:
            if (data.sucesso == 1) {

                //Carregar os dados na tela
                let $divsDash = $('div#dash-estatisticas div');

                $divsDash.eq(0).find('span').html(data.estatisticas.total_ingredientes + ' ingredientes cadastrados');
                $divsDash.eq(1).find('span').html(data.estatisticas.total_receitas + ' receitas cadastradas');
                $divsDash.eq(2).find('span').html(data.estatisticas.total_clientes + ' clientes cadastrados');
                $divsDash.eq(3).find('span').html(data.estatisticas.total_vendas + ' vendas');

                this.loadCharts(data.distribuicao_de_vendas, data.historico_de_vendas);

            } else {
                alert('Deu erro no ajax!')
            }

        },

        consultaListaIngredientes: function () {

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
            if (data.sucesso == 1) {

                //Carregar os dados na tela

                let $tabelaBody = $('div#icheff-ingredientes table tbody');

                $tabelaBody.html('');

                for (let i in data.listaIngredientes) {

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

            } else {
                alert('Deu erro no ajax!')
            }

        },

        consultaListaCategorias: function () {
            console.log('Consulta Categorias');
            return {};
        },

        consultaListaReceitas: function () {
            console.log('Consulta Receitas');
            return {};
        },

        consultaListaUsuarios: function () {
            console.log('Consulta Usuarios');
            return {};
        },

        //Botões modais
        bindBotoesModais: function () {

            let _this = this;

            $('.card-admin-content').on('click', 'div.btn-novo button', function () {
                let $modal = $(this).parents('.card-admin-content').find('.modal');
                let $modalButton = $modal.find('.modal-footer button');
                let secaoPlural = $(this).parents('div.admin-content').attr('id').replace('icheff-', '');
                let secao = secaoPlural.substring(0, secaoPlural.length - 1);

                let novoTxt = (secao == 'ingrediente' ? 'Novo' : 'Nova');

                _this.limparForm($modal.find('form'));

                $modal.find('h5').html(novoTxt + ' ' + secao);
                $modalButton.html('Criar');
                $modalButton.removeAttr('item_id');
                $modalButton.attr('acao', secaoPlural + '/cadastrar');

                $modal.modal();
            });
        },

        bindBotoesSubmeterModais: function () {
            $('.card-admin-content').on('click', 'div.modal-footer button', (event) => {

                let $divContent = $(event.target).parents('div.admin-content');
                let secao = $divContent.attr('id').replace('icheff-', '');
                let $form = $divContent.find('div.modal form');
                let _this = this;

                //Async/Await
                _this['submeterModal' + _this.primeiraMaiuscula(secao)]($form);

            });
        },

        bindBotoesInternosModais: function() {
            $('table.table-ingredientes').on('click','.icon-delete-ingrediente', function(){
                $(this).parents('tr').remove();
            });

            $('div.modal-receitas button#add-ingrediente').on('click', function(){

                let qtd = $('input#inr_quantidade').val();

                let unidade = $('select#inr_id_unidade option:selected').html();
                let unidade_id = $('select#inr_id_unidade option:selected').val();

                let ingrediente = $('select#inr_id_ingrediente option:selected').html();
                let ingrediente_id = $('select#inr_id_ingrediente option:selected').val();

                let $tr = $('<tr>');
                
                $tr.append($('<td>').html(qtd + ' ' + unidade));
                $tr.append($('<td>').html(ingrediente));
                $tr.append($('<td>').html($('<img class="icon-delete-ingrediente" src="img/icons/icon-delete.png">')));

                let jsonIngrediente = JSON.stringify({
                    ingrediente: ingrediente_id,
                    unidade: unidade_id,
                    quantidade: qtd
                });

                $tr.attr('data', jsonIngrediente);

                $('table.table-ingredientes tbody').append($tr);
            });
        },

        submeterModalIngredientes: function ($form) {

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

        submeterModalCategorias: function ($form) {
            console.log('Categorias');
        },

        submeterModalReceitas: function ($form) {
            console.log('Receitas');
        },

        tableDataTable: function () {
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
        menuPrincipalLayout: function () {
            let redimensionaObj = function (nome) {
                let $obj = $(nome);
                let totalPadding = parseInt($obj.css('padding-top')) + parseInt($obj.css('padding-bottom'));
                $obj.height($(window).height() - totalPadding);
            }

            let redimensionaMenu = function () {
                redimensionaObj('aside');
                redimensionaObj('main');
            }

            $(document).ready(redimensionaMenu);
            $(window).resize(redimensionaMenu);
        },

        loadCharts: function (dataDistribuicao, dataHistorico) {

            google.charts.load('current', { packages: ['corechart'] });
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {

                var data = google.visualization.arrayToDataTable([
                    ['Ação', 'Quantidade'],
                    ...dataDistribuicao
                ]);

                var options = {
                    title: 'Distribuição de vendas por categoria',
                    is3D: true,
                    chartArea: { width: '93%', height: '80%', left: '0' }
                };

                var chart = new google.visualization.PieChart(document.getElementById('atividades-chart'));

                chart.draw(data, options);
            }

            google.charts.setOnLoadCallback(drawChart2);

            function drawChart2() {

                var data = new google.visualization.DataTable();
                data.addColumn('date', 'Dia');
                data.addColumn('number', 'Quantidade de vendas');

                let datas = [];

                for(i in dataHistorico) {
                    let d = dataHistorico[i].data.split('/');
                    let qtd = dataHistorico[i].quantidade;
                    let dt = [new Date(d[0], d[1] - 1, d[2]), qtd];
                    datas.push(dt);
                }

                data.addRows(datas);

                var options = {
                    title: 'Histórico de vendas',
                    legend: 'none',
                    hAxis: {
                        format: 'd/M/yyyy',
                        gridlines: { count: 15 }
                    },
                    vAxis: {
                        //gridlines: {color: 'none'},
                        minValue: 0
                    },
                    chartArea: { width: '90%', height: '80%' },
                };

                var chart = new google.visualization.LineChart(document.getElementById('historico-chart'));

                chart.draw(data, options);

            }
        }

    }

})().init();