PROJETO SPRING BOOT

Realizado através de acompanhando do Curso da Udemy : REST API's RESTFul do 0 à Nuvem Com Spring Boot 2.x e Docker


Projeto realizado na : <version>2.1.3.RELEASE</version>

1 - Criando Estrutura no Padrão REST - Representational State Transfer

  Estilo de Arquitetura de software para sistemas distribuídos de hipermídia(www)
  Faz requisições HTTP e suporta diferentes tipos de arquivos.
  
  Conjunto de Restrições:
    1 - WebService (Client - Server)
    2 - Stateless Server - servidor não guarda o estado do cliente
    3 - Cacheavel
    4 - Interface Uniforme
    
   Tipos de Parâmetros:
   
     1 - PATH PARAM
        passado na url OBRIGATÓRIOS (indicado pela /) Exemplo: localhost:8080/api/person/v1/detalhes
    
     2 - QUERY PARAM
        passado na url NÃO OBRIGATÓRIOS (indicado pela ?) Exemplo: localhost:8080/api/person/v1?detalhes=algum
     
     3 - HEADER PARAM
       enviados no HEADER da requisição.
            no Postam na aba HEADER - acrescenta a key(accept/content-type, etc) value ();
            
      4 - BODY PARAM
         enviados no corpo da requisição
   
    Status Code:
      1xx - Informational
      2xx - Success
      3xx - Redirection
      4xx - Cliente Error
      5xx - Server Error
      
     
     Verbos HTTP:    CRUD
        1 - C - Create - POST - Body/Quey/Path/Header Param
        2 - R - Read - GET - Quey/Path/Header Param
        3 - U - Update - PUT OR PATCH - Body/Quey/Path/Header Param
        4 - D - Delete - DELETE - Body/Quey/Path/Header Param
        
     Níveis de Maturidade do Rest:
      Level 0 - Trafega em diferentes APIS com Json e XML
      Level 1 - Recursos - Diferentes Endpoints (GET)
      Level 2 - HTTP Verbs
      Level 3 - HATEOAS - Hypermedia Controls
      Restfull
     
 
 2 - Nossa API está sendo implementado no padrão SpringBoot.
 
  2.1 - Pacotes:
        
        *Controller;
        *Service;
        *Model;
        *Repository
        *Exceptions;
        *Converters;
        *Config;
    
        *Tests - aplicados em nossa camada VO.
        
            VO - Value Object - para não vir dados do cliente direto para nossas Entity, 
                e para possíveis diferenças entre o que o cliente precisa e o que o Servidor precisa,
                response da nossa aplicação e o request da mesma, são em VO.
          
   
   2.1 - Utilizamos o pom adicionando todas as dependência necessárias para desenvolvimento:
   
    2.1.1 - springBoot
    2.1.2 - testes
    2.1.3 - junit
    
    - camada de acesso ao banco
    2.1.4 - data - jpa
    2.1.5 - mysql
    
      no nosso application.properties: nosso config para acesso ao banco de Dados;
        Utilizando MySQL WORKBRENCH para nos ajudar com o acesso e possíveis querys para confirmação de valores e acesso.
        
      Implementamos o padrão de MIGRATIONS com FLYWAY:
      
          v1__nome_arquivo.sql
              create table / insert into , etc
          
          executamos através de duas formas
            1 - adicionamos as configs do flyway no application.properties e buildamos em linha de comando:
                mvn clean package spring-boot : run
                
            2 - adicionamos um plugin no pom e utlizamos o comando
                mvn flyway : migrate
      
      
      - Hypermedia Controls - HATEOAS
      2.1.6 - hateoas
      
          Adicionamos links em todos os métodos da nossa camada de Controller.
          
            reponse.add(linkTo(methodOn(NomeClass.class)findById(id)).withSelfRel());
           
      
      - Mapper
      2.1.7 - dozer-mapper
          Utilizamos o Dozer Mapper para fazer o mapeamento, converter nossas Entitys em VOs ou vice-versa.
       
          - criamos uma classe de DozerConverter.
            adicionamos os métodos que utilizam o Mapper com o método .map(origin, destination);
            
      
      - CONTENT NEGOTIATION
      2.1.8 - xml
      2.1.9 - yaml
      
         Adicionamos o XML e YAML para nossa aplicação poder consumir e produzir XML e YAML, além do json que ela já fazia.
         
         Criamos a classe de WebConfig extends WebMvcConfigurer
         
              *no YAML
                  precisamos adicionar um atributo e mais um método.
                  
                        private static final MediaType MEDIA_TYPE_YML = MediaType.valueOf("application/x-yaml"); 

                        public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                              converters.add(new YamlJackson2HttpMessageConverter());
                        }
         
         Podíamos fazer isso através de 3 formas:
         método de configuração retorno é void e recebe com parâmetro:
         
                 ContentNegotiationConfigurer configurer
                 
          métodos de
         
          1 - via EXTENSÃO
                configurer
                  .favorParameter(false) 
                  .ignoreAcceptHeader(false)
		              .defaultContentType(MediaType.APPLICATION_JSON) 
                  .mediaType("json",MediaType.APPLICATION_JSON) 
                  .mediaType("xml", MediaType.APPLICATION_XML)
                  .mediaType("x-yaml", MEDIA_TYPE_YML);

          2 - via QUERY PARAM
                   configurer 
                    .favorPathExtension(false) 
                    .favorParameter(true)
		                .parameterName("mediaType") 
                    .ignoreAcceptHeader(true)
		                .useRegisteredExtensionsOnly(false)
		                .defaultContentType(MediaType.APPLICATION_JSON) 
                    .mediaType("json", MediaType.APPLICATION_JSON) 
                    .mediaType("xml", MediaType.APPLICATION_XML)
                    .mediaType("x-yaml", MEDIA_TYPE_YML);
          
          3 - via HEADER PARAM
                  configurer
		                .favorPathExtension(false)
		                .favorParameter(false) 
		                .ignoreAcceptHeader(false)
		                .useRegisteredExtensionsOnly(false)
		                .defaultContentType(MediaType.APPLICATION_JSON) 
		                .mediaType("json", MediaType.APPLICATION_JSON) 
		                .mediaType("xml", MediaType.APPLICATION_XML)
		                .mediaType("x-yaml", MEDIA_TYPE_YML);
       
       - SWAGGER
       2.1.10 - swagger
          duas dependencias dos swagger - swagger2 (para add o swagger e habilitar a documentação)  -   /v2/api-docs
                                          ui (para formatar o swagger)                              -   /swagger-ui.html
      
        Melhorando mais ainda nossa aplicação - com SWAGGER - documentação da nossa API
        
        - Adicionamos um aquivo de Config do Swagger
          @Configuration   -  para o Component Scan considerar esse arquivo de config
          @EnableSwagger2    - para habilitar o Swagger
          
         método para config padrão/básica do Swagger:
            
                      @Bean
                        public Docket api() {
                              return new Docket(DocumentationType.SWAGGER_2)
                                .select()
                                .apis(RequestHandlerSelectors.basePackage("br.com.erudio"))
                                .paths(PathSelectors.any())
                                .build()
                                .apiInfo(apiInfo());
                        }
                        
        método para customizar o Swagger:
        
                        private ApiInfo apiInfo() {
                          return new ApiInfo(
                              "RESTfull Api With Spring Boot 2.1.3", 
                              "some description about your API", 
                              "v1", 
                              "terms Of Service Url", 
                              new springfox.documentation.service.Contact("Gizele Correia", "www.nada.com.br", "gizele.ccardoso@gmail.com"), 
                              "license of API", 
                              "license of Url", 
                              Collections.emptyList());
                        }
        colocamos algumas anotações:
          
         @Api(tag = "")     também @Api(value = "", description = "" , tag = "")  // description esta deprecated
         na classe para mudar o nome do Controller
         
         @ApiOperation(value = "")
         nos métodos também adicionamos

   
         
         
      
       
          
 
  
