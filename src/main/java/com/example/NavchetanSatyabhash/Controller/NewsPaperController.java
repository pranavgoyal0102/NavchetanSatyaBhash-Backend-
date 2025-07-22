package com.example.NavchetanSatyabhash.Controller;

import com.example.NavchetanSatyabhash.Models.NewsPaper;
import com.example.NavchetanSatyabhash.Service.NewsPaperService;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/newspaper")
public class NewsPaperController {

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    @GetMapping("/all")
    public ResponseEntity<List<NewsPaper>> getAll() {
        return ResponseEntity.ok(newsPaperService.getAllPaper());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsPaper> findById(@PathVariable String id) {
        Optional<NewsPaper> newsPaper = newsPaperService.findById(id);
        return newsPaper.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{id}/{uid}")
    public void recordViews(@PathVariable String id, @PathVariable String uid) {
        newsPaperService.recordView(id, uid);
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<?> getPdf(@PathVariable String id) {
        try {
            GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(new ObjectId(id))));
            if (file == null) {
                return ResponseEntity.notFound().build();
            }

            GridFsResource resource = gridFsOperations.getResource(file);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header("Content-Disposition", "inline; filename=" + resource.getFilename())
                    .body(resource.getInputStream().readAllBytes());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving PDF file.");
        }
    }
}
