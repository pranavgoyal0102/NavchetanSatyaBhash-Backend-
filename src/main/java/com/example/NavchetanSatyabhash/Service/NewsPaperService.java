package com.example.NavchetanSatyabhash.Service;

import com.example.NavchetanSatyabhash.Models.NewsPaper;
import com.example.NavchetanSatyabhash.Repository.NewspaperRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class NewsPaperService {

    @Autowired
    private NewspaperRepo newspaperRepo;

    public List<NewsPaper> getAllPaper(){
        return newspaperRepo.findAll();
    }

    public Optional<NewsPaper> findById(String id){
        return newspaperRepo.findById(id);
    }

    public void saveNewsPaper(NewsPaper newsPaper){
        newsPaper.setViews(0);
        newsPaper.setDate(LocalDateTime.now());
        newspaperRepo.save(newsPaper);
    }

    public void recordView(String newsId,String uid){
        NewsPaper newsPaper = newspaperRepo.findById(newsId).orElse(null);
        if(newsPaper!= null && !newsPaper.getView().contains(uid)){
            newsPaper.getView().add(uid);
            newsPaper.setViews(newsPaper.getView().size());
            newspaperRepo.save(newsPaper);
        }
    }

    public NewsPaper updatePaper(String id, NewsPaper newsPaper){
        NewsPaper newsPaper1 = newspaperRepo.findById(id).orElse(null);
        if(newsPaper1 != null){
            newsPaper1 = newsPaper;
            newspaperRepo.save(newsPaper1);
            return  newsPaper1;
        }
        return null;
    }

    public void deleteById(String id) {
        newspaperRepo.deleteById(id);
    }
}
