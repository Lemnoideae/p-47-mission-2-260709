package com.ll.wiseSaying;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.TreeMap;

class WiseSayingRepository {
    WiseSayingRepository(String lastId_path, String data_path) throws IOException {
        this.lastId_path = Path.of(lastId_path);
        this.data_path = Path.of(data_path);
        checkPaths();
        this.service = new WiseSayingService(this.data_path);
        this.wise_map = service.initList(this.lastId_path);
        this.new_id_num = 1 + Integer.parseInt(Files.readString(this.lastId_path));
    }
    void addNewWise(Scanner scanner, WiseSayingController controller) {
        service.addNewWise(scanner, controller, wise_map, new_id_num);
        this.new_id_num++;
    }
    String getListString() { return service.getListString(wise_map); }

    void buildList(WiseSayingController controller) throws IOException {
        service.buildList(controller, wise_map, lastId_path, data_path);
    }
    public void modifyWise(Scanner scanner, WiseSayingController controller, final int id) {
        service.modifyWise(scanner, controller, wise_map, id);
    }
    public void deleteWise(WiseSayingController controller, final int id) {
        service.deleteWise(controller, wise_map, id);
    }

    void checkPaths() throws FileNotFoundException {
        String absolutePath;
        if (Files.notExists(this.data_path)) {
            absolutePath = this.data_path.toAbsolutePath().toString();
            throw new FileNotFoundException("Data file not found : " + absolutePath);
        }
        if (Files.notExists(this.lastId_path)) {
            absolutePath = this.lastId_path.toAbsolutePath().toString();
            throw new FileNotFoundException("Last Id file not found : " + absolutePath);
        }
    }

    private final Path lastId_path;
    private final Path data_path;

    private final WiseSayingService service;
    private final TreeMap<Integer, WiseSaying> wise_map;
    private Integer new_id_num;
}
