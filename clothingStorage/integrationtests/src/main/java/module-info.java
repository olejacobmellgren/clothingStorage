open module clothingStorage.restserver {

    requires clothingStorage.client;
    requires transitive clothingStorage.json;

    exports clothingStorage.integrationtests;
}

