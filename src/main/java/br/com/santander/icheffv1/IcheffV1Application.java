package br.com.santander.icheffv1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.santander.icheffv1.model.Ingrediente;
import br.com.santander.icheffv1.model.IngredienteReceita;
import br.com.santander.icheffv1.model.IngredienteUnidade;
import br.com.santander.icheffv1.model.Receita;
import br.com.santander.icheffv1.model.ReceitaCategoria;
import br.com.santander.icheffv1.model.Tipo;
import br.com.santander.icheffv1.model.Usuario;
import br.com.santander.icheffv1.model.Venda;
import br.com.santander.icheffv1.model.VendaRelacao;
import br.com.santander.icheffv1.repository.IngredienteReceitaRepository;
import br.com.santander.icheffv1.repository.IngredienteRepository;
import br.com.santander.icheffv1.repository.IngredienteUnidadeRepository;
import br.com.santander.icheffv1.repository.ReceitaCategoriaRepository;
import br.com.santander.icheffv1.repository.ReceitaRepository;
import br.com.santander.icheffv1.repository.UsuarioRepository;
import br.com.santander.icheffv1.repository.VendaRelacaoRepository;
import br.com.santander.icheffv1.repository.VendaRepository;

@SpringBootApplication
public class IcheffV1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IcheffV1Application.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
                .allowedOrigins("*");
			}
		};
	}

	@Autowired
	private IngredienteUnidadeRepository ingredienteUnidadeRepository;
	
	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@Autowired
	private IngredienteReceitaRepository ingredienteReceitaRepository;
	
	@Autowired
	private ReceitaCategoriaRepository receitaCategoriaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private VendaRelacaoRepository vendaRelacaoRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		//Instancia o admin caso não exista ;)
		if(usuarioRepository.findByTipo(Tipo.ADMINISTRADOR).orElse(new ArrayList<Usuario>()).isEmpty()){
			Usuario administrador = new Usuario(
				null,
				"iCheff Admin",
				"admin@icheff.com.br",
				"hackme",
				LocalDate.now(),
				LocalDateTime.now(),
				"310821459",
				"95212599067",
				"Praça da Sé",
				"s/n",
				null,
				"01001-001",
				"Centro",
				"São Paulo",
				"São Paulo",
				"1155554321",
				"11987654321"
			);
			administrador.setTipo(Tipo.ADMINISTRADOR);
			usuarioRepository.save(administrador);
		}
		
		//Instancia cliente caso não exista ;)
		if(usuarioRepository.findByTipo(Tipo.CLIENTE).orElse(new ArrayList<Usuario>()).isEmpty()){
			Usuario usuario1 = new Usuario(
				null,
				"Maria José",
				"maria@gmail.com",
				"hackme",
				LocalDate.parse("1985-05-04"),
				LocalDateTime.now(),
				"303069028",
				"78325540028",
				"Rua Ofélia",
				"42",
				"Apto 45",
				"05423-110",
				"Pinheiros",
				"São Paulo",
				"São Paulo",
				"1155554321",
				"11987654321"
			);
			
			Usuario usuario2 = new Usuario(
					null,
					"José Maria",
					"jose@gmail.com",
					"hackme",
					LocalDate.parse("1980-12-11"),
					LocalDateTime.now(),
					"472403679",
					"60028024010",
					"Av. Paulista",
					"2200",
					"Casa",
					"05401-010",
					"Jardim Paulista",
					"São Paulo",
					"São Paulo",
					"1155554321",
					"11987654321"
			);
			
			usuario1.setTipo(Tipo.CLIENTE);
			usuario2.setTipo(Tipo.CLIENTE);
			
			this.usuarioRepository.save(usuario1);
			this.usuarioRepository.save(usuario2);
		}
		
		//Instancia as unidades quando não tem! ;)
		if(ingredienteUnidadeRepository.count() == 0) {
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"un","Unidade","Unidades","Unidade"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"g","Grama","Gramas","Grama"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"kg","Quilograma","Quilogramas","Quilograma"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"L","Litro","Litros","Litro"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"mL","Mililitro","Mililitros","Mililitro"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"cs","Colher de sopa","Colheres de sopa","Colher de sopa"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"cch","Colher de chá","Colheres de chá","Colher de chá"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"csb","Colher de sobremesa","Colheres de sobremesa","Colher de sobremesa"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"xcf","Xícara de Café","Xícaras de Café","Xícara de Café"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"xíc","Xícara de Chá","Xícaras de Chá","Xícara de Chá"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"cp","Copo","Copos","Copo"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"pt","Pote","Potes","Pote"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"pc","Pacote","Pacotes","Pacote"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"lt","Lata","Latas","Lata"));
			ingredienteUnidadeRepository.save(new IngredienteUnidade(null,"fl","Folha","Folhas","Folha"));
		}
		
		//Instancia alguns ingredientes quando não tem! ;)
		if(ingredienteRepository.count() == 0) {
			
			IngredienteUnidade unidade = ingredienteUnidadeRepository.findByUnidadeSigla("un").get().get(0);
			IngredienteUnidade grama = ingredienteUnidadeRepository.findByUnidadeSigla("g").get().get(0);
			IngredienteUnidade kg = ingredienteUnidadeRepository.findByUnidadeSigla("kg").get().get(0);
			IngredienteUnidade litro = ingredienteUnidadeRepository.findByUnidadeSigla("L").get().get(0);
			IngredienteUnidade ml = ingredienteUnidadeRepository.findByUnidadeSigla("mL").get().get(0);
			IngredienteUnidade lata = ingredienteUnidadeRepository.findByUnidadeSigla("lt").get().get(0);
			IngredienteUnidade pote = ingredienteUnidadeRepository.findByUnidadeSigla("pt").get().get(0);
			
			ingredienteRepository.save(new Ingrediente(null, "Farinha", true, 0.0025, grama));
			ingredienteRepository.save(new Ingrediente(null, "Farinha", true, 2.5, kg));
			ingredienteRepository.save(new Ingrediente(null, "Leite", true, 2.8, litro));
			ingredienteRepository.save(new Ingrediente(null, "Sal", true, 0.01, grama));
			ingredienteRepository.save(new Ingrediente(null, "Sal grosso", true, 0.02, grama));
			ingredienteRepository.save(new Ingrediente(null, "Açúcar", true, 0.002, grama));
			ingredienteRepository.save(new Ingrediente(null, "Açúcar", true, 2, kg));
			ingredienteRepository.save(new Ingrediente(null, "Macarrão", true, 1.5, grama));
			ingredienteRepository.save(new Ingrediente(null, "Massa de lazanha", true, 0.55, grama));
			ingredienteRepository.save(new Ingrediente(null, "Carne moída", true, 0.0124, grama));
			ingredienteRepository.save(new Ingrediente(null, "Carne moída", true, 12.4, kg));
			ingredienteRepository.save(new Ingrediente(null, "Côco ralado", true, 0.08, grama));
			ingredienteRepository.save(new Ingrediente(null, "Leite condensado", true, 5.8, lata));
			ingredienteRepository.save(new Ingrediente(null, "Leite condensado", true, 0.79, ml));
			ingredienteRepository.save(new Ingrediente(null, "Manteiga", true, 1.35, grama));
			ingredienteRepository.save(new Ingrediente(null, "Manteiga", true, 8.35, lata));
			ingredienteRepository.save(new Ingrediente(null, "Creme de leite", true, 4.5, lata));
			ingredienteRepository.save(new Ingrediente(null, "Cream cheese", true, 1.35, pote));
			ingredienteRepository.save(new Ingrediente(null, "Fermento", true, 0.0035, grama));
			ingredienteRepository.save(new Ingrediente(null, "Óleo", true, 0.15, ml));
			ingredienteRepository.save(new Ingrediente(null, "Ovo", true, 0.35, unidade));
			
		}
		
		//Instancia as categorias quando não tem! ;)
		if(this.receitaCategoriaRepository.count() == 0) {
			receitaCategoriaRepository.save(new ReceitaCategoria(null,"Variados",false));
			receitaCategoriaRepository.save(new ReceitaCategoria(null,"Peixes e frutos do mar",false));
			receitaCategoriaRepository.save(new ReceitaCategoria(null,"Fitness",false));
			receitaCategoriaRepository.save(new ReceitaCategoria(null,"Veganos e Vegetarianos",true));
		}
		
		//Instancia uma receita caso não exista nenhuma ;)
		if(this.receitaRepository.count() == 0) {
			
			ReceitaCategoria categoria = this.receitaCategoriaRepository.findAll().get(0);
			
			Receita receita = new Receita(
				null,
				"Bolo de côco",
				"http://localhost:8080/imagens/bolo-de-coco.jpg",
				"https://www.youtube.com/watch?v=bxMxOsypcck",
				categoria,
				12L,
				"Misture todos os ingredientes o liquidificador e coloque para assar!",
				25.99
			);
			
			List<IngredienteReceita> ingredientes = new ArrayList<IngredienteReceita>();
			
			IngredienteUnidade grama = ingredienteUnidadeRepository.findByUnidadeSigla("g").get().get(0);
			IngredienteUnidade litro = ingredienteUnidadeRepository.findByUnidadeSigla("L").get().get(0);
			IngredienteUnidade lata = ingredienteUnidadeRepository.findByUnidadeSigla("lt").get().get(0);
			
			Ingrediente farinha = ingredienteRepository.findByNomeAndIngredienteUnidade("Farinha", grama).get();
			ingredientes.add(new IngredienteReceita(null, 500, receita, farinha));
			
			Ingrediente leite = ingredienteRepository.findByNomeAndIngredienteUnidade("Leite", litro).get();
			ingredientes.add(new IngredienteReceita(null, 0.5, receita, leite));
			
			Ingrediente leiteCondensado = ingredienteRepository.findByNomeAndIngredienteUnidade("Leite condensado", lata).get();
			ingredientes.add(new IngredienteReceita(null, 1, receita, leiteCondensado));
			
			Ingrediente coco = ingredienteRepository.findByNomeAndIngredienteUnidade("Côco ralado", grama).get();
			ingredientes.add(new IngredienteReceita(null, 150, receita, coco));
			
			receita.setIngredientes(ingredientes);
			
			this.receitaRepository.save(receita);
			
			this.ingredienteReceitaRepository.saveAll(ingredientes);
			
			//Outra receita!
			ReceitaCategoria categoria2 = this.receitaCategoriaRepository.findAll().get(2);
			
			Receita receita2 = new Receita(
				null,
				"Bolo de leite condensado",
				"http://localhost:8080/imagens/bolo-de-leite-condensado.jpg",
				"https://www.youtube.com/watch?v=bxMxOsypcck",
				categoria2,
				12L,
				"Misture todos os ingredientes o liquidificador e coloque para assar!",
				19.99
			);
			
			List<IngredienteReceita> ingredientes2 = new ArrayList<IngredienteReceita>();
			
			ingredientes2.add(new IngredienteReceita(null, 500, receita2, farinha));
			ingredientes2.add(new IngredienteReceita(null, 0.5, receita2, leite));
			ingredientes2.add(new IngredienteReceita(null, 1, receita2, leiteCondensado));
			
			receita.setIngredientes(ingredientes2);
			
			this.receitaRepository.save(receita2);
			
			this.ingredienteReceitaRepository.saveAll(ingredientes2);
			
		}
		
		//Instancia algumas vendas caso não exista nenhuma ;)
		if(this.vendaRepository.count() == 0) {
			
			Receita receita = this.receitaRepository.findById(1L).get();
			Receita receita2 = this.receitaRepository.findById(2L).get();
			
			//Venda 1
			Usuario usuario1 = this.usuarioRepository.findById(2L).get(); 
			
			Venda venda1 = new Venda(
				null,
				true,
				LocalDateTime.now(),
				LocalDateTime.now(),
				usuario1
			);
			
			this.vendaRepository.save(venda1);
			
			VendaRelacao vendaRelacao1 = new VendaRelacao(null, 2L, receita, venda1);
			this.vendaRelacaoRepository.save(vendaRelacao1);
			
			VendaRelacao vendaRelacao2 = new VendaRelacao(null, 1L, receita2, venda1);
			this.vendaRelacaoRepository.save(vendaRelacao2);
			
			//Venda 2
			Usuario usuario2 = this.usuarioRepository.findById(3L).get(); 
			
			Venda venda2 = new Venda(
				null,
				true,
				LocalDateTime.now().minus(2, ChronoUnit.DAYS),
				LocalDateTime.now().minus(2, ChronoUnit.DAYS),
				usuario2
			);
			
			this.vendaRepository.save(venda2);
			
			VendaRelacao vendaRelacao3 = new VendaRelacao(null, 5L, receita, venda2);
			this.vendaRelacaoRepository.save(vendaRelacao3);
			
			VendaRelacao vendaRelacao4 = new VendaRelacao(null, 2L, receita2, venda2);
			this.vendaRelacaoRepository.save(vendaRelacao4);
			
			//Venda 3
			Venda venda3 = new Venda(
				null,
				true,
				LocalDateTime.now().minus(2, ChronoUnit.DAYS),
				LocalDateTime.now().minus(2, ChronoUnit.DAYS),
				usuario1
			);
			
			this.vendaRepository.save(venda3);
			
			VendaRelacao vendaRelacao5 = new VendaRelacao(null, 5L, receita, venda3);
			this.vendaRelacaoRepository.save(vendaRelacao5);
			
			//Venda 4
			Venda venda4 = new Venda(
				null,
				true,
				LocalDateTime.now().minus(4, ChronoUnit.DAYS),
				LocalDateTime.now().minus(4, ChronoUnit.DAYS),
				usuario2
			);
			
			this.vendaRepository.save(venda4);
			
			VendaRelacao vendaRelacao6 = new VendaRelacao(null, 5L, receita2, venda4);
			this.vendaRelacaoRepository.save(vendaRelacao6);
			
			//Venda 5
			Venda venda5 = new Venda(
				null,
				true,
				LocalDateTime.now().minus(8, ChronoUnit.DAYS),
				LocalDateTime.now().minus(8, ChronoUnit.DAYS),
				usuario1
			);
			
			this.vendaRepository.save(venda5);
			
			VendaRelacao vendaRelacao7 = new VendaRelacao(null, 5L, receita2, venda5);
			this.vendaRelacaoRepository.save(vendaRelacao7);
			
		}
		
	}
	
}