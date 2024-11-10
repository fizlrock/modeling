# Vue SPA Template

Vue + Vite + TypeScript + ESLint + Prettier template for single-page applications

This is a standard web frontend template using Vue and Vue Router.

It is intended for use with the VSCode IDE.

## Usage

Download the master branch of the repository as a template and modify it.

```sh
curl -OL https://github.com/curegit/vue-spa-template/archive/refs/heads/master.zip
unzip master.zip
mv vue-spa-template-master new-project
cd new-project
npm install
code .
```

## Start the dev server

```sh
npm run dev
```

Use the `--port` option to specify the port.

```sh
npm run dev -- --port 8888
```

## Build

```sh
npm run build
```

Use the `--base` option to change the public path to be nested.

```sh
npm run build -- --base /some/nested/path
```

## License

[MIT](LICENSE)
