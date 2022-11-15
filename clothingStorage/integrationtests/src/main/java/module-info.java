module clothingStorage.integrationtests {
    requires spring.boot;
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;

    requires clothingStorage.client;
    requires transitive clothingStorage.json;
    requires transitive clothingStorage.restserver;

    opens clothingStorage.integrationtests to spring.core;

    exports clothingStorage.integrationtests;
}

