module clothingStorage.client {

    requires java.net.http;
    requires transitive clothingStorage.core;
    requires transitive clothingStorage.json;

    exports clothingStorage.client;
}

