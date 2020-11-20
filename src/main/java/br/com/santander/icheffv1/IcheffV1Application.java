package br.com.santander.icheffv1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
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
import br.com.santander.icheffv1.repository.IngredienteRepository;
import br.com.santander.icheffv1.repository.IngredienteUnidadeRepository;
import br.com.santander.icheffv1.repository.ReceitaCategoriaRepository;
import br.com.santander.icheffv1.repository.ReceitaRepository;
import br.com.santander.icheffv1.repository.UsuarioEnderecoRepository;
import br.com.santander.icheffv1.repository.UsuarioRepository;

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
	private ReceitaCategoriaRepository receitaCategoriaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
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
			IngredienteUnidade litro = ingredienteUnidadeRepository.findByUnidadeSigla("kg").get().get(0);
			IngredienteUnidade ml = ingredienteUnidadeRepository.findByUnidadeSigla("mL").get().get(0);
			IngredienteUnidade lata = ingredienteUnidadeRepository.findByUnidadeSigla("lt").get().get(0);
			IngredienteUnidade pote = ingredienteUnidadeRepository.findByUnidadeSigla("pt").get().get(0);
			
			ingredienteRepository.save(new Ingrediente(null, "Farinha", true, 0.0025, grama));
			ingredienteRepository.save(new Ingrediente(null, "Farinha", true, 2.50, kg));
			ingredienteRepository.save(new Ingrediente(null, "Leite", true, 2.80, litro));
			ingredienteRepository.save(new Ingrediente(null, "Sal", true, 0.01, grama));
			ingredienteRepository.save(new Ingrediente(null, "Macarrão", true, 1.5, grama));
			ingredienteRepository.save(new Ingrediente(null, "Massa de lazanha", true, 0.55, grama));
			ingredienteRepository.save(new Ingrediente(null, "Carne moída", true, 0.01240, grama));
			ingredienteRepository.save(new Ingrediente(null, "Carne moída", true, 12.40, kg));
			ingredienteRepository.save(new Ingrediente(null, "Côco ralado", true, 5.80, grama));
			ingredienteRepository.save(new Ingrediente(null, "Leite condensado", true, 5.8, lata));
			ingredienteRepository.save(new Ingrediente(null, "Leite condensado", true, 0.79, ml));
			ingredienteRepository.save(new Ingrediente(null, "Manteiga", true, 1.35, grama));
			ingredienteRepository.save(new Ingrediente(null, "Manteiga", true, 8.35, lata));
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
		/*
		if(this.receitaRepository.count() == 0) {
			
			Receita receita = new Receita(
				null,
				"Bolo de côco",
				"http://localhost:8080/imagens/bolo-de-coco.jpg",
				"https://www.youtube.com/watch?v=bxMxOsypcck",
				"Misture todos os ingredientes o liquidificador e coloque para assar!",
				25.0
			);
			
			List<IngredienteReceita> ingredientes = new ArrayList<IngredienteReceita>();
			
			//ingredientes.add();
			
			receita.setIngredientes(ingredientes);
			
			this.receitaRepository.save(receita);
			
		}
		*/
	}
	
}