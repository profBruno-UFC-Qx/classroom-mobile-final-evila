[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/AR7CADm8)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23543905)
# :checkered_flag: HomeMates

Um aplicativo focado em conectar estudantes da UFC Quixadá e IFCE para o compartilhamento de repúblicas, casas e quartos. O grande diferencial do app é o foco na realidade local: ele destaca o nível de conforto térmico do imóvel (presença de janelas, posição do sol, ventilação) e calcula a proximidade do local com as rotas e paradas do ônibus da universidade.

## :technologist: Membros da equipe

554162 - Évila Maria Vasconcelos de Araújo (Grade Curricular de 2013)

## :bulb: Objetivo Geral
O app visa resolver a atual fragmentação de informações, acabando com a dependência de anúncios informais espalhados por dezenas de grupos de WhatsApp — que frequentemente são desconhecidos ou de difícil acesso para os alunos recém-chegados. Além disso, o projeto busca aplicar conceitos avançados de desenvolvimento mobile, como persistência de dados, consumo de APIs e geolocalização, entregando uma solução tecnológica alinhada à logística urbana e climática de Quixadá.
## :eyes: Público-Alvo
Estudantes universitários da UFC e IFCE (Campus Quixadá) que estão chegando na cidade e buscam um local para morar, assim como veteranos que já possuem um imóvel alugado e procuram colegas para dividir as despesas mensais.

## :star2: Impacto Esperado

Espera-se democratizar o acesso à informação sobre moradias, reduzindo a dificuldade, o tempo e o estresse financeiro que os estudantes enfrentam ao procurar um local adequado. Ao centralizar os anúncios e priorizar informações cruciais como o trajeto do ônibus intercampi e a temperatura do imóvel, o app ajudará os alunos a tomarem decisões que impactam diretamente na sua qualidade de vida, mobilidade e rendimento acadêmico.

## :triangular_flag_on_post:	 Principais funcionalidades da aplicação

1. **Gerenciamento de Imóveis (Requisito CRUD & Navegação):** Os usuários poderão cadastrar, visualizar, editar e excluir anúncios de vagas. O fluxo contará com múltiplas telas (Feed de anúncios, Detalhes do imóvel, Perfil do usuário e Formulário de cadastro).
2. **Busca de Endereço Automatizada (Requisito API Externa):** Integração com a API do **ViaCEP** para preencher automaticamente os dados de bairro e rua a partir do CEP, facilitando o cadastro de novos quartos.
3. **Localização e Rota do Ônibus (Requisito Uso de GPS/Mapas):** A tela de detalhes do imóvel exibirá um mapa integrado apontando a localização da casa e calculando a distância/tempo de caminhada até a parada de ônibus intercampi mais próxima.
4. **Motor de Preferências do Estudante (Requisito Persistência Local - DataStore):** Utilização do **Preferences DataStore** para salvar localmente o perfil de busca do usuário (ex: orçamento máximo e preferência restrita por quartos bem ventilados). Ao abrir o aplicativo, essas configurações são lidas de forma assíncrona para filtrar automaticamente o feed de anúncios, poupando o estudante de reconfigurar os filtros a cada novo acesso.
---

> [!WARNING]
> Daqui em diante o README.md só deve ser preenchido no momento da entrega final.

##  Tecnologias: 
Liste aqui as tecnologias e bibliotecas que foram utilizadas no projeto.

---

## Instruções para Execução
[Inclua instruções claras sobre como rodar o projeto localmente. Isso é crucial para que você possa testá-lo nas próximas entregas. **Somente caso haja alguma coisa diferente do usual**

```bash
# Clone o repositório
git clone [https://docs.github.com/pt/repositories/creating-and-managing-repositories/about-repositories](https://docs.github.com/pt/repositories/creating-and-managing-repositories/about-repositories)

# Navegue para o diretório
cd [nome-do-repositorio]

# Siga as instruções específicas para a sua tecnologia...
