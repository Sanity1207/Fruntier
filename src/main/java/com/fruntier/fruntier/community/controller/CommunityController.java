package com.fruntier.fruntier.community.controller;


import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.RequireTokenValidation;
import com.fruntier.fruntier.community.domain.*;
import com.fruntier.fruntier.community.exception.CommentException;
import com.fruntier.fruntier.community.exception.ArticleException;
import com.fruntier.fruntier.community.service.ArticleService;
import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/community")
@Slf4j
public class CommunityController {
    private final ArticleService articleService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public CommunityController(ArticleService articleService, JwtTokenService jwtTokenService) {
        this.articleService = articleService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping
    @RequireTokenValidation
    public String communityMainPagePaged(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "page-size", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "search", required = false, defaultValue = "") String searchKey,
            Model model
    ) {
        Page<Article> articleListPage = articleService.getArticleListPage(page, pageSize, searchKey);
        long totalPages = articleListPage.getTotalPages();
        long startPage = page >= 3 ? page - 2 : 1;
        List<Long> pageList = new ArrayList<>();
        for (long i = startPage; i < startPage + 5; i++) {
            pageList.add(i);
            if (i == totalPages)
                break;
        }

        model.addAttribute(articleListPage.toList());
        model.addAttribute("pageList", pageList);
        model.addAttribute("curPage", page);
        model.addAttribute("totalPages", totalPages);
        return "community/community-main";
    }

    @GetMapping("/article/{articleId}")
    @RequireTokenValidation
    public String articlePage(
            @PathVariable long articleId, Model model,
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        log.info("user={}", user);
        try {
            Article article = articleService.getArticle(articleId);
            model.addAttribute("article", article);
            model.addAttribute("comments", article.getComments());
            model.addAttribute("user", user);
        }
        // Article doesn't exist -> redirects to article main page
        catch (ArticleException articleException) {
            return "redirect:/community";
        }
        return "community/article";
    }

    @ResponseBody
    @PostMapping("/article/{articleId}/comment/new")
    @RequireTokenValidation
    public String addNewComment(
            @PathVariable long articleId,
            @RequestBody CommentDTO commentDTO,
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        try {
            Article article = articleService.getArticle(articleId);
            articleService.saveComment(article, commentDTO, user);
        } catch (ArticleException articleException) {
            return "redirect:/community";
        }
        return "";
    }

    @ResponseBody
    @PatchMapping("/article/{articleId}/comment/{commentId}")
    @RequireTokenValidation
    public String editComment(
            @PathVariable long articleId,
            @PathVariable long commentId,
            @RequestBody CommentDTO commentDTO,
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        try {
            System.out.println("commentDTO.getContent() = " + commentDTO.getContent());
            articleService.editComment(user, articleId, commentId, commentDTO.getContent());
        } catch (CommentException e) {
            return "redirect:/article/{articleId}/comment/{commentId}"; // refresh the page
        }
        return "";
    }

    @ResponseBody
    @DeleteMapping("/article/{articleId}/comment/{commentId}")
    @RequireTokenValidation
    public String deleteComment(
            @PathVariable long articleId,
            @PathVariable long commentId,
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        try {
            articleService.deleteComment(user, articleId, commentId);
        } catch (CommentException e) {
            return "redirect:/article/{articleId}/comment/{commentId}"; // refresh the page
        }
        return "";
    }

    @GetMapping("/article/new")
    @RequireTokenValidation
    public String newArticlePage() {
        return "community/new-article";
    }

    @ResponseBody
    @PostMapping("/article/new")
    @RequireTokenValidation
    public Article saveNewArticle(
            @RequestBody ArticleDTO articleDTO,
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        log.debug("articleDTO={}", articleDTO);
        log.info("user={}", user);
        return articleService.saveNewArticle(articleDTO, user);
    }

    @GetMapping("/article/{articleId}/edit")
    @RequireTokenValidation
    public String editArticleView(
            @PathVariable long articleId,
            @CookieValue(value = "authToken", required = false) String authCookie,
            HttpServletRequest request,
            Model model
    ) {
        User user = (User) request.getAttribute("validatedUser");
        try {
            Article article = articleService.getArticle(articleId);
            model.addAttribute("article", article);
        } catch (ArticleException e) {
            return "redirect: /community";
        }
        return "community/edit-article";
    }

    @PatchMapping("/article/{articleId}/edit")
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    @RequireTokenValidation
    public String editArticle(
            @PathVariable long articleId,
            @RequestBody ArticleDTO articleDTO,
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        try {
            articleService.editArticle(articleId, user, articleDTO);
            return "redirect:/community/article/{articleId}";
        } catch (ArticleException e) {
            return "redirect:/community";
        }
    }

    @ResponseBody
    @DeleteMapping("/article/{articleId}")
    @RequireTokenValidation
    public String deleteArticle(
            @PathVariable long articleId,
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        try {
            articleService.deleteArticle(articleId, user);
        } catch (ArticleException e) {
            return "redirect: /community";
        }
        return "";
    }
}
