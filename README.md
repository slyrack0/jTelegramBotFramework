### jTelegramBotFramework

It's a framework based on 

- [Spring boot](https://github.com/spring-projects/spring-boot) - General purpose java framework that makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".
- [TelegramBots](https://github.com/rubenlagus/TelegramBots) - A simple to use library to create Telegram Bots in Java

that simplifies bot development by:

- session control
- state control
- commands isolation
- dialogs isolation
- automatic extract general information about bot user

see [framework-demo-bot](https://github.com/slyrack0/framework-demo-bot) for framework usage example.

### Abstract processing flow
```
               receive updates from telegram

       ─────────────────────────────────────────


              ┌──────────────────────────┐
              │                          │
              │      ┌────────────┐      │
              │      │ Middleware │      │  - execute service logic 
              │      └─────┬──────┘      │
              │            │             │
              │      ┌─────▼──────┐      │
              │      │ Command    │      │  - execute business logic
              │      └─────┬──────┘      │
              │            │             │
              │      ┌─────▼──────┐      │
              │      │ View       │      │  - build result message
              │      └────────────┘      │
              │                          │
              └──────────────────────────┘


       ─────────────────────────────────────────

               send Message to telegram


```

### Session management

Session is key value storage.

The default is in memory session storage implementation. In this implementation data don't save between runs.

