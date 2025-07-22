package com.example.NavchetanSatyabhash.Controller;

import com.example.NavchetanSatyabhash.Config.PdfCompressor;
import com.example.NavchetanSatyabhash.Models.Article;
import com.example.NavchetanSatyabhash.Models.NewsPaper;
import com.example.NavchetanSatyabhash.Service.ArticleService;
import com.example.NavchetanSatyabhash.Service.NewsPaperService;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private GridFsOperations gridFsOperations;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private NewsPaperService newsPaperService;

    @GetMapping
    public String adminHome() {
        return "admin_home";
    }



    @GetMapping("/article/form")
    public String showArticleForm() {
        return "upload_article";
    }

    @PostMapping("/article/save")
    public String saveArticle(@RequestParam String title,
                              @RequestParam String description,
                              @RequestParam String category,
                              @RequestParam String liveOrVideoOrAd,
                              @RequestParam(value = "images", required = false) MultipartFile[] imageFiles,
                              @RequestParam(value = "videoUrls", required = false) List<String> videoUrls) {
        try {
            Article article = new Article();
            article.setTitle(title);
            article.setLiveOrVideoOrAd(liveOrVideoOrAd);
            article.setDescription(description);
            article.setCategory(category);
            article.setViews(0);
            article.setDate(LocalDateTime.now());

            List<String> imageList = new ArrayList<>();
            if (imageFiles != null) {
                for (MultipartFile file : imageFiles) {
                    if (!file.isEmpty()) {
                        byte[] bytes = file.getBytes();
                        String base64 = Base64.getEncoder().encodeToString(bytes);
                        imageList.add(base64);
                    }
                }
            }
            article.setImages(imageList);

            if (videoUrls != null) {
                List<String> filtered = videoUrls.stream()
                        .filter(link -> link != null && !link.trim().isEmpty())
                        .collect(Collectors.toList());
                article.setVideos(filtered);
            }

            articleService.saveArticle(article);
            return "redirect:/admin/articles";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/article/form";
        }
    }





    @GetMapping("/articles")
    public String showAllArticles(Model model) {
        List<Article> articles = articleService.getAllArticle();
        model.addAttribute("articles", articles);
        return "list_articles";
    }

    @PostMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable String id) {
        articleService.deleteById(id);
        return "redirect:/admin/articles";
    }


    @GetMapping("/newspaper/form")
    public String showNewspaperForm() {
        return "upload_newspaper";
    }

    @PostMapping("/newspaper/save")
    public String saveNewspaper(@RequestParam String title,
                                @RequestParam("file") MultipartFile file,
                                HttpServletRequest request) {
        try {
            // Compress and store in GridFS
            byte[] compressedPdf = PdfCompressor.compressPdf(file.getInputStream());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedPdf);

            ObjectId fileId = gridFsTemplate.store(inputStream, file.getOriginalFilename(), "application/pdf");

            // Construct file access URL
            String baseUrl = request.getScheme() + "://" + request.getServerName() + "/newspaper" ;
            String pdfUrl = baseUrl + "/pdf/" + fileId.toHexString();

            NewsPaper newsPaper = new NewsPaper();
            newsPaper.setTitle(title);
            newsPaper.setDate(LocalDateTime.now());
            newsPaper.setViews(0);
            newsPaper.setNewsPaper(pdfUrl);

            newsPaperService.saveNewsPaper(newsPaper);
            return "redirect:/admin/newspapers";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/newspaper/form";
        }
    }
    @GetMapping("/newspapers")
    public String showAllNewspapers(Model model) {
        List<NewsPaper> newspapers = newsPaperService.getAllPaper();
        model.addAttribute("newspapers", newspapers);
        return "list_newspapers";
    }

    @PostMapping("/newspaper/delete/{id}")
    public String deleteNewspaper(@PathVariable String id) {
        newsPaperService.deleteById(id);
        return "redirect:/admin/newspapers";
    }
}
