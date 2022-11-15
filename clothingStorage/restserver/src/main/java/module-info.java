open module clothingStorage.restserver {

    requires transitive clothingStorage.json;

    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.boot.autoconfigure;

    requires clothingStorage.core;

    exports clothingStorage.restserver;
}

