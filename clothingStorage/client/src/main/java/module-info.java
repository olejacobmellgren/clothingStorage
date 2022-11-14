module clothingStorage.client {

    requires java.net.http;
    requires transitive clothingStorage.json;
    requires org.json;

    exports clothingStorage.client;
}

