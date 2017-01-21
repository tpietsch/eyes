({
    baseUrl: ".",
    mainConfigFile: "requireConfig.js",
    name: "main",
    optimize: "uglify",
    out: "main-built.js",
    findNestedDependencies: true,
    paths: {
        requireLib:"node_modules/requirejs/require",
    }
})
