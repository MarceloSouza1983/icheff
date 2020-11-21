const BASE_URL = 'http://localhost:8080';

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
            $('aside div.menu a').eq(0).trigger('click');

            //$('#icheff-receitas button').eq(0).trigger('click');
        },

        //Primeira letra maiúscula
        primeiraMaiuscula: (s) => {
            if (typeof s !== 'string')
                return ''
            return s.charAt(0).toUpperCase() + s.slice(1);
        },

        //Só primeira letra maiúscula
        soPrimeiraMaiuscula: (s) => {
            if (typeof s !== 'string')
                return ''
            return s.charAt(0).toUpperCase() + s.slice(1).toLowerCase();
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
            $form.find('textarea').val('');
            $form.find('select option:selected').removeAttr('selected');
            $form.find('table.table-ingredientes tbody').html('');
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

                _this['preencheEditModal' + _this.primeiraMaiuscula(secao)]($form, json);

                let $btn = $modalEdit.find('.modal-footer button');
                $btn.html('Salvar')
                $btn.attr('acao', item_id);

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
                let $modalDelete = $('#modalDelete');

                $.ajax({
                    url: BASE_URL + '/api/' + secao + '/' + item_id,
                    type: 'DELETE',
                    dataType: 'json',
                    cache: false,
                    success: function(){
                        $modalDelete.modal('hide');
                        _this['consultaLista' + _this.primeiraMaiuscula(secao)]();
                    },
                    error: function(data){
                        alert(data.message);
                    }
                });

            });
        },

        //Preencher campos do modal
        preencheEditModalIngredientes: function ($form, json) {
            $form.find('input#ingrediente-nome').val(json.nome);

            $form.find('select#ingrediente-unidade option:selected').removeAttr('selected');
            $form.find('select#ingrediente-unidade option[value="' + json.ingredienteUnidade.id + '"]').attr('selected', '');

            $form.find('input#ingrediente-custo').val(json.custo.toString().replace('.', ','));

            $form.find('select#ingrediente-ativo option:selected').removeAttr('selected');
            $form.find('select#ingrediente-ativo option[value="' + (json.ativo?1:0) + '"]').attr('selected', '');
        },

        preencheEditModalCategorias: function ($form, json) {
            $form.find('input#cat_nome').val(json.nome);

            $form.find('select#cat_vegana option:selected').removeAttr('selected');
            $form.find('select#cat_vegana option[value="' + (json.vegana?1:0) + '"]').attr('selected', '');
        },

        preencheEditModalReceitas: function ($form, json) {
            $form.find('input#rec_nome').val(json.nome);
            $form.find('input#rec_imagem').val(json.imagem);
            $form.find('input#rec_link_youtube').val(json.linkVideo);
            $form.find('input#rec_preco').val(json.preco.toString().replace('.',','));

            $form.find('select#rec_id_categoria option[value="' + json.categoriaId + '"]').attr('selected', '');
            $form.find('select#rec_ativa option[value="' + (json.ativa?'1':'0') + '"]').attr('selected', '');
            $form.find('select#rec_porcoes option[value="' + json.porcoes + '"]').attr('selected', '');

            $form.find('textarea#rec_descricao').val(json.descricao);

            //Preenche a lista os ingredientes

            let $tbodyIngredientes = $form.find('table.table-ingredientes');

            $tbodyIngredientes.find('tbody').html('');

            for(let i in json.ingredientes){
                let ing = json.ingredientes[i];
                
                let $tr = $('<tr>');
                
                $tr.append($('<td>').html(ing.quantidade + ' ' + ing.unidade));
                $tr.append($('<td>').html(ing.nome));
                $tr.append($('<td>').html('<img class="icon-delete-ingrediente" src="img/icons/icon-delete.png">'));
                
                let jsonIngrediente = {
                    id: ing.id,
                    ingrediente:{
                        id: ing.ingredienteId
                    },
                    quantidade: ing.quantidade
                }

                $tr.attr('data', JSON.stringify(jsonIngrediente));

                $tbodyIngredientes.append($tr);
                
            }
        },

        //Consultas
        consultaListaDashboard: function () {

            $('ol.breadcrumb li.active').html('Dashboard');

            let _this = this;

            $.ajax({
                url: BASE_URL + '/api/dashboard',
                type: 'GET',
                dataType: 'json',
                cache: false,
                success: function(data){

                    //Carregar os dados na tela
                    let $divsDash = $('div#dash-estatisticas div');
        
                    $divsDash.eq(0).find('span').html(
                        data.ingredientesCadastrados
                        + (data.ingredientesCadastrados > 1 ? ' ingredientes cadastrados' : ' ingrediente cadastrado')
                    );

                    $divsDash.eq(1).find('span').html(
                        data.receitasCadastradas
                        + (data.receitasCadastradas > 1 ? ' receitas cadastradas' : ' receita cadastrada')
                    );

                    $divsDash.eq(2).find('span').html(
                        data.clientesCadastrados
                        + (data.clientesCadastrados > 1 ? ' clientes cadastrados' : ' cliente cadastrado')
                    );

                    $divsDash.eq(3).find('span').html(
                        data.vendasRealizadas
                        + (data.vendasRealizadas > 1 ? ' vendas realizadas' : ' venda realizada')
                    );

                    setTimeout(()=>{
                        _this.loadCharts(data.distribuicaoDeVendas, data.historicoDeVendas);
                    },500)

                },
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(JSON.parse(jqXHR.responseText));
                }
            });

        },

        consultaListaIngredientes: async function () {

            $('ol.breadcrumb li.active').html('Ingredientes');

            //Preencher as unidades padrão
            let unidades = await this.loadListaUnidades();

            let $selectUnidades = $('div#icheff-ingredientes select#ingrediente-unidade').html('');

            for(let i in unidades){
                let $option = $('<option>').val(unidades[i].id).html(unidades[i].unidadeSigla);
                $selectUnidades.append($option)
            }
            //Fim unidades padrão

            $.ajax({
                url: BASE_URL + '/api/ingredientes',
                type: 'GET',
                dataType: 'json',
                cache: false,
                success: function(data){

                    let $tabelaBody = $('div#icheff-ingredientes table tbody');
        
                    $tabelaBody.html('');

                    for (let i in data) {
        
                        let $edit = $('<img class="icon-edit" src="img/icons/icon-edit.png">');
                        let $delete = $('<img class="icon-delete" src="img/icons/icon-delete.png">');
        
                        let d = data[i];

                        $edit.attr('data', JSON.stringify(d));
        
                        let $tr = $('<tr>');
                        let $tdOpcoes = $('<td>');
        
                        $tr.append('<td>' + d.id + '</td>');
                        $tr.append('<td>' + d.nome + '</td>');
                        $tr.append('<td>' + d.ingredienteUnidade.unidadeSigla + '</td>');
                        $tr.append('<td>' + d.custo.toString().replace('.',',') + '</td>');
                        $tr.append('<td>' + (d.ativo?'Sim':'Não') + '</td>');
        
                        $edit.attr('item_id', d['id']);
                        $tdOpcoes.append($edit);
        
                        $delete.attr('item_id', d['id']);
                        $tdOpcoes.append($delete);
        
                        $tr.append($tdOpcoes);
        
                        $tabelaBody.append($tr);
        
                    }

                },
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(JSON.parse(jqXHR.responseText));
                }
            });

        },

        consultaListaCategorias: function(){

            $('ol.breadcrumb li.active').html('Categorias');

            $.ajax({
                url: BASE_URL + '/api/categorias',
                type: 'GET',
                dataType: 'json',
                cache: false,
                success: function(data){

                    let $tabelaBody = $('div#icheff-categorias table tbody');

                    $tabelaBody.html('');

                    for (let i in data) {

                        let $edit = $('<img class="icon-edit" src="img/icons/icon-edit.png">');
                        let $delete = $('<img class="icon-delete" src="img/icons/icon-delete.png">');

                        let d = data[i];

                        $edit.attr('data', JSON.stringify(d));

                        let $tr = $('<tr>');
                        let $tdOpcoes = $('<td>');

                        $tr.append('<td>' + d.id + '</td>');
                        $tr.append('<td>' + d.nome + '</td>');
                        $tr.append('<td>' + (d.vegana?'Sim':'Não') + '</td>');
                        $tr.append('<td>' + d.quantidadeReceitas + '</td>');

                        $edit.attr('item_id', d.id);
                        $tdOpcoes.append($edit);

                        $delete.attr('item_id', d.id);
                        $tdOpcoes.append($delete);

                        $tr.append($tdOpcoes);

                        $tabelaBody.append($tr);

                    }

                },
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(JSON.parse(jqXHR.responseText));
                }
            });

        },

        consultaListaReceitas: async function () {

            $('ol.breadcrumb li.active').html('Receitas');

            //Load do modal de edição/novo
            let $selectCategorias = $('select#rec_id_categoria').html('');
            let $selectIngredientes = $('select#inr_id_ingrediente').html('');
            let $selectUnidades = $('select#inr_id_unidade').html('');

            let categorias = await this.loadListaCategorias();

            for(let i in categorias){
                let nome = categorias[i].nome + (categorias[i].vegana?' (vegana)':'');
                let $option = $('<option>').val(categorias[i].id).html(nome);
                $selectCategorias.append($option)
            }
            
            let ingredientes = await this.loadListaIngredientes();

            for(let i in ingredientes){

                let unidadeIngrediente = ingredientes[i].ingredienteUnidade.unidadeSigla;
                let nomeIngrediente = ingredientes[i].nome + ' (' + unidadeIngrediente + ')';

                let $option = $('<option>')
                    .val(ingredientes[i].id)
                    .attr('unidade', unidadeIngrediente)
                    .html(nomeIngrediente);

                $selectIngredientes.append($option)
            }

            $.ajax({
                url: BASE_URL + '/api/receitas',
                type: 'GET',
                dataType: 'json',
                cache: false,
                success: function(data){

                    let $tabelaBody = $('div#icheff-receitas table tbody').eq(0);
        
                    $tabelaBody.html('');

                    for (let i in data) {
        
                        let $edit = $('<img class="icon-edit" src="img/icons/icon-edit.png">');
                        let $delete = $('<img class="icon-delete" src="img/icons/icon-delete.png">');
        
                        let d = data[i];
        
                        let $tr = $('<tr>');
                        let $tdOpcoes = $('<td>');
        
                        $tr.append('<td>' + d.id + '</td>');
                        $tr.append('<td>' + d.nome + '</td>');
                        $tr.append('<td>' + d.categoria + '</td>');
                        $tr.append('<td>' + (d.ingredientes ? d.ingredientes.length : 0) + '</td>');
                        $tr.append('<td>' + d.porcoes + '</td>');
                        $tr.append('<td>' + d.custo + '</td>');
                        $tr.append('<td>' + d.preco + '</td>');
                        $tr.append('<td>' + (d.ativa?'Sim':'Não') + '</td>');
        
                        let ingredientesJson = JSON.stringify(d);
        
                        $edit.attr('data', ingredientesJson);
                        $edit.attr('item_id', d.id);
                        $tdOpcoes.append($edit);
        
                        $delete.attr('item_id', d.id);
                        $tdOpcoes.append($delete);
        
                        $tr.append($tdOpcoes);
        
                        $tabelaBody.append($tr);
        
                    }

                },
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(JSON.parse(jqXHR.responseText));
                }

            });

        },

        consultaListaUsuarios: function (){

            $('ol.breadcrumb li.active').html('Usuários');

            let _this = this;

            $.ajax({
                url: BASE_URL + '/api/usuarios',
                type: 'GET',
                dataType: 'json',
                cache: false,
                success: function(data){

                    let $tabelaBody = $('div#icheff-usuarios table tbody').eq(0);
        
                    $tabelaBody.html('');
        
                    for (let i in data) {
        
                        let d = data[i];
        
                        let $tr = $('<tr>');
        
                        $tr.append('<td>' + d.id + '</td>');
                        $tr.append('<td>' + d.nome + '</td>');
                        $tr.append('<td>' + _this.soPrimeiraMaiuscula(d.tipo) + '</td>');
                        $tr.append('<td>' + d.login + '</td>');
                        $tr.append('<td>' + d.quantidadeCompras + '</td>');
                        $tr.append('<td>' + d.dataCadastro + '</td>');
        
                        $tabelaBody.append($tr);
        
                    }

				},
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(JSON.parse(jqXHR.responseText));
                }
            });
        },

        consultaListaVendas: function (){

            $('ol.breadcrumb li.active').html('Vendas');

            $.ajax({
                url: BASE_URL + '/api/vendas',
                type: 'GET',
                dataType: 'json',
                cache: false,
                success: function(data){

                    let $tabelaBody = $('div#icheff-vendas table tbody').eq(0);
        
                    $tabelaBody.html('');
        
                    for (let i in data) {
        
                        let d = data[i];
        
                        let $tr = $('<tr>');

                        let date = new Date(d['dataVenda']);
                        let datePayment = new Date(d['dataPagamento']);

                        let localDateBR = (date) => {
                            return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' ' + date.getHours() + ':' + date.getMinutes();
                        }
        
                        $tr.append('<td>' + d['id'] + '</td>');
                        $tr.append('<td>' + d['nome'] + '</td>');
                        $tr.append('<td>' + d['endereco'] + '</td>');
                        $tr.append('<td>' + localDateBR(date) + '</td>');
                        $tr.append('<td>' + localDateBR(datePayment) + '</td>');
                        $tr.append('<td>' + d['pagamentoRealizado'] + '</td>');
        
                        $tabelaBody.append($tr);
        
                    }

				},
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(JSON.parse(jqXHR.responseText));
                }
            });
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

                if(secao=='receita'){
                    $modal.find('input#inr_quantidade').val(1);
                }

                $modal.find('h5').html(novoTxt + ' ' + secao);
                $modalButton.html('Criar');
                $modalButton.removeAttr('item_id');
                $modalButton.attr('acao', 'cadastrar');

                $modal.modal();
            });
        },

        bindBotoesSubmeterModais: function () {
            $('.card-admin-content').on('click', 'div.modal-footer button', (event) => {

                let $divContent = $(event.target).parents('div.admin-content');
                let acao = $(event.target).attr('acao');
                let secao = $divContent.attr('id').replace('icheff-', '');
                let $form = $divContent.find('div.modal form');
                let _this = this;

                _this['submeterModal' + _this.primeiraMaiuscula(secao)]($form, acao);

            });
        },

        bindBotoesInternosModais: function() {
            $('table.table-ingredientes').on('click','.icon-delete-ingrediente', function(){
                $(this).parents('tr').remove();
            });

            $('div.modal-receitas button#add-ingrediente').on('click', function(){

                let $inputQuantidade = $('input#inr_quantidade');
                let qtd = $inputQuantidade.val();

                qtd.replace(',','.');
                qtd = parseFloat(qtd);

                if(qtd<=0 || !qtd){
                    $inputQuantidade.focus();
                    return;
                }

                let unidade = $('select#inr_id_ingrediente option:selected').attr('unidade');

                let ingrediente = $('select#inr_id_ingrediente option:selected').html();
                let ingrediente_id = $('select#inr_id_ingrediente option:selected').val();

                let $tr = $('<tr>');
                
                $tr.append($('<td>').html(qtd + ' ' + unidade));
                $tr.append($('<td>').html(ingrediente));
                $tr.append($('<td>').html($('<img class="icon-delete-ingrediente" src="img/icons/icon-delete.png">')));

                let jsonIngrediente = JSON.stringify({
                    ingrediente:{
                        id: ingrediente_id
                    },
                    quantidade: qtd
                });

                $tr.attr('data', jsonIngrediente);

                $('table.table-ingredientes tbody').append($tr);
            });
        },

        submeterModalIngredientes: function ($form, acao) {

            let data = {
                id: (acao=='cadastrar'?0:acao),
                nome: $form.find('input#ingrediente-nome').val(),
                ingredienteUnidade: {
                    id: $form.find('select#ingrediente-unidade option:selected').val()
                },
                custo: $form.find('input#ingrediente-custo').val().replace(',','.'),
                ativo: ($form.find('select#ingrediente-ativo option:selected').val() == '1' ? true : false)
            }

            //Verificações
            if(data.nome.trim().length === 0){
                alert('Preencha o nome do ingrediente!');
                return;
            }

            if(data.custo.trim().length === 0){
                alert('Preencha o custo do ingrediente!');
                return;
            }
            //Fim verificações

            let _this = this;

            $.ajax({
                url: BASE_URL + '/api/ingredientes' + (acao=='cadastrar'?'':'/' + data.id),
                method: acao=='cadastrar'?'POST':'PUT',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function(data){
                    _this.consultaListaIngredientes();
                    $form.parents('div.admin-content').find('div.modal').modal('hide');
                },
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(JSON.parse(jqXHR.responseText));
                }
            });

        },

        submeterModalCategorias: function ($form, acao) {
            
            let data = {
                nome: $form.find('input#cat_nome').val(),
                vegana: ($form.find('select#cat_vegana option:selected').val()=='1' ? true : false),
            }

            //Verificações
            if(data.nome.trim().length === 0){
                alert('Preencha o nome da categoria!');
                return;
            }
            //Fim verificações

            let _this = this;

            $.ajax({
                url: BASE_URL + '/api/categorias' + (acao=='cadastrar'?'':'/' + acao),
                method: acao=='cadastrar'?'POST':'PUT',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function(data){
                    _this.consultaListaCategorias();
                    $form.parents('div.admin-content').find('div.modal').modal('hide');
                },
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(JSON.parse(jqXHR.responseText));
                }
            });

        },

        submeterModalReceitas: function ($form, acao) {
           
            let data = {
                nome: $form.find('input#rec_nome').val(),
                preco: $form.find('input#rec_preco').val().replace(',','.'),
                imagem: $form.find('input#rec_imagem').val(),
                linkVideo: $form.find('input#rec_link_youtube').val(),
                ativa: ($form.find('select#rec_ativa option:selected').val() == '1' ? true : false),
                descricao: $form.find('textarea#rec_descricao').val(),
                porcoes: $form.find('select#rec_porcoes option:selected').val(),

                receitaCategoria: {
                    id: $form.find('select#rec_id_categoria option:selected').val()
                },

                ingredientes: []
            }

            //Ingredientes selecionados
            $('table.table-ingredientes tbody tr').each(function(){
                let dataJson = $(this).attr('data');
                let jsonIngrediente = JSON.parse(dataJson);
                data.ingredientes.push(jsonIngrediente);
            });

            //Verificações
            if(data.nome.trim().length === 0){
                alert('Preencha o nome da receita!');
                return;
            }

            if(data.imagem.trim().length === 0){
                alert('Preencha a imagem da receita!');
                return;
            }

            if(data.linkVideo.trim().length === 0){
                alert('Preencha o link para o YouTube da receita!');
                return;
            }

            if(data.descricao.trim().length === 0){
                alert('Preencha a descrição da receita!');
                return;
            }

            if(typeof data.ingredientes[0] === 'undefined'){
                alert('Inclua pelo menos um ingrediente para a receita!');
                return;
            }
            //Fim verificações

            let _this = this;

            $.ajax({
                url: BASE_URL + '/api/receitas' + (acao=='cadastrar'?'':'/' + acao),
                method: acao=='cadastrar'?'POST':'PUT',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function(data){
                    _this.consultaListaReceitas();
                    $form.parents('div.admin-content').find('div.modal').modal('hide');
                },
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(JSON.parse(jqXHR.responseText));
                }
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

        loadListas: async function(path){
            return await fetch(BASE_URL + '/api/' + path)
                .then((response) => response.json());
        },

        loadListaCategorias: function(){
            return this.loadListas('categorias');
        },

        loadListaIngredientes: function(){
            return this.loadListas('ingredientes');
        },

        loadListaUnidades: function(){
            return this.loadListas('ingredienteunidade');
        },

        loadCharts: function (dataDistribuicao, dataHistorico) {

            google.charts.load('current', { packages: ['corechart'] });
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {

                let arrayDados = [
                    ['Categoria', 'Quantidade']
                ];

                for(let i in dataDistribuicao){
                    arrayDados.push([
                        dataDistribuicao[i].categoria,
                        dataDistribuicao[i].quantidade
                    ]);
                }

                var data = google.visualization.arrayToDataTable(arrayDados);

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

                for(let i in dataHistorico) {
                    let d = dataHistorico[i].data.split('-');
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