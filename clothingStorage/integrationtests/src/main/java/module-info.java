open module clothingStorage.restserver {

    requires clothingStorage.client;
    requires clothingStorage.restserver;
    requires transitive clothingStorage.json;

    exports clothingStorage.integrationtests;
}

