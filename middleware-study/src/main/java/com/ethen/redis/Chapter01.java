package com.ethen.redis;

import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * redis-in-action 第一章 demo
 * <p>
 * note:
 * hash => 每篇文章的元数据以及投票信息（文章id、标题、作者、url、发布时间、投票数量。。。） key = article
 * <p>
 * zset => 根据文章创建时间排序   key = time
 * zset => 根据创建时间+投票进行排序    key = score
 */
public class Chapter01 {
    private static final int ONE_WEEK_IN_SECONDS = 7 * 86400;
    private static final int VOTE_SCORE = 432;
    private static final int ARTICLES_PER_PAGE = 25;
    private static final char COLON = ':';


    private static final String VOTE_KEY = "voted" + COLON;
    private static final String TIME_KEY = "time" + COLON;
    private static final String ARTICLE_KEY = "article" + COLON;
    private static final String SCORE_KEY = "score" + COLON;

    private void init() {
        Jedis conn = new Jedis("localhost");
        conn.select(15);
    }

    /**
     * 发布文章
     */
    public String postArticle(Jedis conn, String user, String title, String link) {
        //文章Id
        String articleId = String.valueOf(conn.incr("article" + COLON));
        //投票
        String voted = VOTE_KEY + articleId;
        conn.sadd(voted, user);
        //设置投票过期事件为一星期(秒)
        conn.expire(voted, ONE_WEEK_IN_SECONDS);
        //当前时间秒数
        long now = System.currentTimeMillis() / 1000;
        String article = ARTICLE_KEY + articleId;
        Map<String, String> articleData = new HashMap<String, String>();
        articleData.put("title", title);
        articleData.put("link", link);
        articleData.put("user", user);
        articleData.put("now", String.valueOf(now));
        articleData.put("votes", "1");
        //用散列结构存储文章元数据
        conn.hmset(article, articleData);
        //有序集合存储文章的投票数据（计算排行榜）
        conn.zadd(SCORE_KEY, now + VOTE_SCORE, article);
        //有序集合按时间排序文章
        conn.zadd(TIME_KEY, now, article);
        return articleId;
    }

    /**
     * 给指定文章投票（点赞、顶）
     *
     * @param conn    redis连接
     * @param user    投票用户
     * @param article 文章的key
     */
    public void articleVote(Jedis conn, String user, String article) {
        //超过一星期的文章不能评论
        long cutoff = (System.currentTimeMillis() / 1000) - ONE_WEEK_IN_SECONDS;
        if (conn.zscore(TIME_KEY, article) < cutoff) {
            return;
        }
        String articleId = article.substring(article.indexOf(COLON) + 1);
        //如果添加进去说明该用户在此之前没有投票过
        if (conn.sadd(VOTE_KEY + articleId, user) == 1) {
            //给文章投票加分
            conn.zincrby(SCORE_KEY, VOTE_SCORE, article);
            //这篇文章投票总数+1
            conn.hincrBy(article, "votes", 1);

        }
    }


    /**
     * 获取文章列表
     *  step1 => 从有序集合中获取id列表
     *  step2 => 遍历获取文章元数据信息并返回文章元数据列表
     *
     * @param conn
     * @param page
     * @param order
     * @return
     */
    public List<Map<String, String>> getArticles(Jedis conn, int page, String order) {

        int start = (page - 1) * ARTICLES_PER_PAGE;
        int end = start + ARTICLES_PER_PAGE - 1;
        Set<String> articleKeys = conn.zrange(order, start, end);

        List<Map<String, String>> articles = new LinkedList<Map<String, String>>();
        for (String articleKey : articleKeys) {
            Map<String, String> articleData = conn.hgetAll(articleKey);
            articles.add(articleData);
        }
        return articles;
    }

}
