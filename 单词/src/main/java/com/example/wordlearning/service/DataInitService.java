package com.example.wordlearning.service;

import com.example.wordlearning.entity.User;
import com.example.wordlearning.entity.Word;
import com.example.wordlearning.repository.UserRepository;
import com.example.wordlearning.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DataInitService implements CommandLineRunner {
    
    @Autowired
    private WordRepository wordRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // 初始化管理员账户
        initializeAdmin();
        
        // 检查是否已有数据
        if (wordRepository.count() == 0) {
            initializeWords();
        }
    }
    
    private void initializeAdmin() {
        // 检查是否已有管理员账户
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            
            userRepository.save(admin);
            System.out.println("创建了默认管理员账户: admin/admin123");
        }
    }
    
    private void initializeWords() {
        // 添加一些基础单词
        Word[] words = {
            new Word("hello", "你好", "/həˈloʊ/", "Hello, how are you? 你好，你好吗？"),
            new Word("world", "世界", "/wɜːrld/", "Welcome to the world! 欢迎来到这个世界！"),
            new Word("love", "爱", "/lʌv/", "I love you. 我爱你。"),
            new Word("book", "书", "/bʊk/", "This is a good book. 这是一本好书。"),
            new Word("study", "学习", "/ˈstʌdi/", "I study English every day. 我每天学习英语。"),
            new Word("computer", "电脑", "/kəmˈpjuːtər/", "I use a computer to work. 我用电脑工作。"),
            new Word("school", "学校", "/skuːl/", "I go to school by bus. 我坐公交车去学校。"),
            new Word("teacher", "老师", "/ˈtiːtʃər/", "My teacher is very kind. 我的老师很和蔼。"),
            new Word("student", "学生", "/ˈstuːdənt/", "She is a good student. 她是一个好学生。"),
            new Word("friend", "朋友", "/frend/", "He is my best friend. 他是我最好的朋友。"),
            new Word("family", "家庭", "/ˈfæməli/", "I love my family. 我爱我的家庭。"),
            new Word("house", "房子", "/haʊs/", "This is my house. 这是我的房子。"),
            new Word("water", "水", "/ˈwɔːtər/", "I drink water every day. 我每天喝水。"),
            new Word("food", "食物", "/fuːd/", "Chinese food is delicious. 中国菜很美味。"),
            new Word("time", "时间", "/taɪm/", "Time is money. 时间就是金钱。"),
            new Word("money", "钱", "/ˈmʌni/", "Money can't buy happiness. 金钱买不到快乐。"),
            new Word("happy", "快乐的", "/ˈhæpi/", "I am very happy today. 我今天很快乐。"),
            new Word("beautiful", "美丽的", "/ˈbjuːtɪfl/", "She is very beautiful. 她很美丽。"),
            new Word("good", "好的", "/ɡʊd/", "This is a good idea. 这是个好主意。"),
            new Word("bad", "坏的", "/bæd/", "That's not a bad thing. 那不是坏事。"),
            new Word("big", "大的", "/bɪɡ/", "This is a big house. 这是一座大房子。"),
            new Word("small", "小的", "/smɔːl/", "I have a small car. 我有一辆小汽车。"),
            new Word("new", "新的", "/nuː/", "I bought a new phone. 我买了一部新手机。"),
            new Word("old", "旧的", "/oʊld/", "This is an old building. 这是一座旧建筑。"),
            new Word("young", "年轻的", "/jʌŋ/", "She is very young. 她很年轻。"),
            new Word("work", "工作", "/wɜːrk/", "I work in an office. 我在办公室工作。"),
            new Word("play", "玩", "/pleɪ/", "Children like to play games. 孩子们喜欢玩游戏。"),
            new Word("eat", "吃", "/iːt/", "I eat breakfast at 7 AM. 我早上7点吃早餐。"),
            new Word("drink", "喝", "/drɪŋk/", "I drink coffee in the morning. 我早上喝咖啡。"),
            new Word("sleep", "睡觉", "/sliːp/", "I sleep 8 hours every night. 我每晚睡8小时。"),
            new Word("walk", "走路", "/wɔːk/", "I walk to work every day. 我每天走路上班。"),
            new Word("run", "跑", "/rʌn/", "I run in the park. 我在公园里跑步。"),
            new Word("read", "读", "/riːd/", "I read books before sleep. 我睡前读书。"),
            new Word("write", "写", "/raɪt/", "I write in my diary. 我写日记。"),
            new Word("listen", "听", "/ˈlɪsən/", "I listen to music. 我听音乐。"),
            new Word("speak", "说话", "/spiːk/", "I speak Chinese and English. 我说中文和英文。"),
            new Word("watch", "看", "/wɑːtʃ/", "I watch TV in the evening. 我晚上看电视。"),
            new Word("learn", "学习", "/lɜːrn/", "I learn something new every day. 我每天学习新东西。"),
            new Word("teach", "教", "/tiːtʃ/", "My mother teaches me cooking. 我妈妈教我做饭。"),
            new Word("help", "帮助", "/help/", "Can you help me? 你能帮助我吗？"),
            new Word("think", "思考", "/θɪŋk/", "I think this is correct. 我认为这是正确的。"),
            new Word("know", "知道", "/noʊ/", "I know the answer. 我知道答案。"),
            new Word("understand", "理解", "/ˌʌndərˈstænd/", "I understand your problem. 我理解你的问题。"),
            new Word("remember", "记住", "/rɪˈmembər/", "Please remember this word. 请记住这个单词。"),
            new Word("forget", "忘记", "/fərˈɡet/", "Don't forget to call me. 别忘了给我打电话。"),
            new Word("important", "重要的", "/ɪmˈpɔːrtənt/", "This is very important. 这很重要。"),
            new Word("interesting", "有趣的", "/ˈɪntrəstɪŋ/", "This book is very interesting. 这本书很有趣。"),
            new Word("difficult", "困难的", "/ˈdɪfɪkəlt/", "This question is difficult. 这个问题很难。"),
            new Word("easy", "容易的", "/ˈiːzi/", "This game is very easy. 这个游戏很容易。"),
            new Word("fast", "快的", "/fæst/", "He runs very fast. 他跑得很快。"),
            new Word("slow", "慢的", "/sloʊ/", "The turtle moves slowly. 乌龟移动得很慢。")
        };
        
        for (Word word : words) {
            wordRepository.save(word);
        }
        
        System.out.println("初始化了 " + words.length + " 个单词");
    }
}