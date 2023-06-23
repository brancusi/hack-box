# Hack Box

The basic box for hacking in clojure.

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

### Development

1. Create `dev-config.edn` in the root dir
2. Add the following vars

```edn

;; WARNING
;; The dev-config.edn file is used for local environment variables, such as database credentials.
;; This file is listed in .gitignore and will be excluded from version control by Git.

{:dev true
 :port 5050
 ;; when :nrepl-port is set the application starts the nREPL server on load
 :nrepl-port 7070

 ;; secrets

 ;;  Hack Box used to authorize inbound requests
 :api-key "Hack Box API KEY"
 }

```

## Running

Using Calva fire up the start repl and connect command for best repl experience

Otherwise start lein manually

```bash
lein run
```

## Building

To build the uberjar

```bash
lein uberjar
```

To start the uberjar locally with env vars

```bash
java -Dconf=dev-config.edn -jar target/uberjar/hack-box.jar
```
