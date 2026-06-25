# Resolve TI

**Sistema de Gerenciamento de Chamados de Suporte Técnico**

Aplicativo Android para registro, acompanhamento e controle de chamados de suporte técnico, desenvolvido em Java com persistência local (SQLite) e em nuvem (Back4App).

## Funcionalidades

- **Menu Lateral** (Drawer Navigation) com acesso rápido a todas as telas
- **Cadastro de Chamados** com título, descrição, local e status (Aberto, Em andamento, Concluído)
- **Captura de Foto** pela câmera do dispositivo, vinculada ao chamado
- **Listagem de Chamados** em RecyclerView com foto, título, local e status
- **Detalhes do Chamado** com visualização da foto e registro de solução
- **Filtros** por status, tipo e período de datas
- **Armazenamento em Nuvem** via Back4App (Parse Server)
- **Estatísticas** com cards de total, abertos, em andamento e concluídos
- **Tela Sobre** com informações do sistema e integrantes

## Tecnologias

- **Linguagem:** Java
- **Banco Local:** SQLite (SQLiteOpenHelper)
- **Armazenamento Nuvem:** Back4App (Parse SDK)
- **UI:** Material Design 3 (Material Components)
- **Imagens:** Glide, FileProvider, ActivityResultContracts
- **Min SDK:** 21 | **Target:** 33

## Estrutura do Projeto

```
app/src/main/java/com/empresa/chamados/
├── ChamadosApplication.java
├── activities/
│   ├── MainActivity.java
│   ├── NovoChamadoActivity.java
│   ├── ListaChamadosActivity.java
│   ├── AtendimentoChamadoActivity.java
│   ├── EstatisticasActivity.java
│   ├── FiltrosChamadosActivity.java
│   └── SobreActivity.java
├── adapters/
│   └── ChamadosAdapter.java
├── database/
│   ├── DatabaseHelper.java
│   └── ChamadoDAO.java
├── models/
│   └── Chamado.java
└── utils/
    └── DateUtils.java
```

## Como Executar

1. Abra o projeto no **Android Studio**
2. Configure o SDK do Android (compileSdk 33)
3. Para usar o Back4App, edite `ChamadosApplication.java` com suas credenciais
4. Conecte seu celular ou use um emulador
5. Clique em **Run** ▶

## Back4App

Para ativar o armazenamento em nuvem:

1. Crie uma conta em [back4app.com](https://www.back4app.com)
2. Crie um novo App
3. Em **App Settings → Security & Keys**, copie o **Application ID** e **Client Key**
4. Cole os valores em `ChamadosApplication.java`

## Licença

Projeto acadêmico.
