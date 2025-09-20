# 🎬 Movie List App

Aplicativo Android desenvolvido em **Kotlin** usando **Jetpack Compose** e **Room** para gerenciar uma lista de filmes para assistir.

## ✨ Funcionalidades

- 📃 **Listar filmes**: exibe todos os filmes cadastrados.
- ➕ **Adicionar filmes**: insere título e status (assistido/não assistido).
- ✏️ **Editar filmes**: altera título e/ou status.
- 🗑 **Excluir filmes**: remove filmes da lista.
- 🎞 **Filmes em Cartaz (API TMDB)**: lista filmes em cartaz consumindo API pública do The Movie Database.
- 📥 **Adicionar à Minha Lista a partir dos Filmes em Cartaz**: botão para salvar diretamente os filmes exibidos na aba “Filmes em Cartaz” no banco local.

## 🏗️ Arquitetura

- **UI**: Jetpack Compose (Material 3).
- **Persistência**: Room Database.
- **Padrão**: MVVM (Model-View-ViewModel).
- **API**: Retrofit + Gson para TMDB.
- **Imagens**: Coil Compose.

## 📱 Como executar

Clone este repositório:

```bash
git clone https://github.com/silaao/movies-list.git
Abra o projeto no Android Studio, configure sua chave de API do TMDB no local indicado no código (ApiClient.kt) e execute o app.

🚧 Em Produção

O aplicativo ainda está em desenvolvimento. Próximas funcionalidades planejadas:

Tela de detalhes do filme com informações mais completas.

Filtros e busca de filmes.

Melhorias no design, animações e testes automatizados.

Possibilidade de login para salvar lista em nuvem.
