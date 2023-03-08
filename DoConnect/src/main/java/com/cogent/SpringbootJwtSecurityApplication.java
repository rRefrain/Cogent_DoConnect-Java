package com.cogent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cogent.entity.Answer;
import com.cogent.entity.AnswerVote;
import com.cogent.entity.Chat;
import com.cogent.entity.ERole;
import com.cogent.entity.Message;
import com.cogent.entity.Question;
import com.cogent.entity.QuestionDTO;
import com.cogent.entity.QuestionVote;
import com.cogent.entity.Role;
import com.cogent.entity.User;
import com.cogent.repository.AnswerRepository;
import com.cogent.repository.ChatRepository;
import com.cogent.repository.MessageRepository;
import com.cogent.repository.QuestionRepository;
import com.cogent.repository.RoleRepository;
import com.cogent.repository.UserRepository;
import com.cogent.service.QuestionService;
import com.cogent.util.Time;

@SpringBootApplication
public class SpringbootJwtSecurityApplication {
	
	 @Autowired
	 PasswordEncoder encoder;
	 
	 @Autowired
	 private RoleRepository roleRepository;
		
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private QuestionRepository questionRepository;
	 
	 @Autowired
	 private QuestionService questionService;
	 
	 @Autowired
	 private AnswerRepository answerRepository;
	 
	 @Autowired
	 private ChatRepository chatRepository;
	 
	 @Autowired
	 private MessageRepository messageRepository;
	 	
	    @PostConstruct
	    public void initUsers() {
	    	  Set<Role> roles = new HashSet<>();
	    	  roles.add(roleRepository.findByName(ERole.ROLE_USER).get());
	    	  
	    	  Set<Role> admin = new HashSet<>();
	    	  admin.add(roleRepository.findByName(ERole.ROLE_USER).get());
	    	  admin.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
	    	  admin.add(roleRepository.findByName(ERole.ROLE_MODERATOR).get());
	    	
	        List<User> users = Stream.of(
	                new User(null, "mr.blueblue", "penguinuser",  encoder.encode("penguinpassword"), "penguinemail@gmail.com", roles),
	                new User(null, "mr.duffy", "bearuser",  encoder.encode("bearpassword"), "bearemail@gmail.com", roles),
	                new User(null, "mr.mango", "catuser",  encoder.encode("catpassword"), "catemail@gmail.com", admin),
	                new User(null, "mr.bongbong", "birduser",  encoder.encode("birdpassword"), "birdemail@gmail.com", admin),
	                new User(null, "mr.going", "doguser",  encoder.encode("dogpassword"), "dogemail@gmail.com", roles)
	        ).collect(Collectors.toList());
	        userRepository.saveAll(users);
	    }
	    

	    @PostConstruct
	    public void initQA() {
	        List<Question> questions = Stream.of(
	                new Question(null, 0, new HashSet<QuestionVote>(),"SomeDescription", "SomeImage", "SomeDateTime", "open", "someTopic",
	                		"someTitle", new ArrayList<Answer>(), "Someone", null),
	                
	                new Question(null, 0, new HashSet<QuestionVote>(), "Hello How are you?", "https://img.freepik.com/premium-vector/hello-word-memphis-background_136321-401.jpg",
	                		Time.getTimeNow(), "open", "hello",
	                		"Urgent!", new ArrayList<Answer>(), "bearuser", null),
	                
	                new Question(null, 0, new HashSet<QuestionVote>(), "White rice or brown?", "https://www.acouplecooks.com/wp-content/uploads/2022/02/How-to-Cook-Rice-002s.jpg",
	                		Time.getTimeNow(), "open", "food",
	                		"Only one right answer", new ArrayList<Answer>(), "penguinuser", null),
	                
	                new Question(null, 0, new HashSet<QuestionVote>(), "Chicken or Pork?", "https://cdn.britannica.com/07/183407-050-C35648B5/Chicken.jpg",
	                		Time.getTimeNow(), "closed", "food",
	                		"Huh?", new ArrayList<Answer>(), "doguser", "birduser"),
	                
	                new Question(null, 0, new HashSet<QuestionVote>(), "Why no rice?", "https://images.squarespace-cdn.com/content/v1/54f7b161e4b05ad03a600b41/1538572872623-THWMDYT74XRDDU4S3FPP/What-Is-Your-Why.jpg?format=1000w",
	                		Time.getTimeNow(), "closed", "food",
	                		"Important", new ArrayList<Answer>(), "birduser", "catuser")
	        ).collect(Collectors.toList());
	        questionRepository.saveAll(questions);
	        
	        	 	QuestionDTO dto = new QuestionDTO();
	        	 	dto.setVote(1);
	        	 	dto.setUserVoter("bearuser");
	        	 	questionService.vote(dto, 2l);
	        	 	
	        	 	
	        	 	QuestionDTO dto2 = new QuestionDTO();
	        	 	dto2.setVote(1);	        	 
	        	 	dto2.setUserVoter("catuser");
	        	 	questionService.vote(dto2, 2l);
	        	 	
	        List<Answer> answers = Stream.of(
	    			//id, voteTally, votes, descriptionAnswer, imgSource, approved, dateTime, question, createdBy, ApprovedBy
	    			new Answer (null, 0, new ArrayList<AnswerVote>(), "someDescription", "someImage", false, 
	    					Time.getTimeNow(), questionRepository.findById(2l).get(), "someone", "someoneElse"), 
	    			
	    			new Answer (null, 0, new ArrayList<AnswerVote>(), "I am Fine", "https://images.squarespace-cdn.com/content/v1/604a9531f77ff4178d4a7cdc/6bbe6ac6-b3a4-40b4-b8ab-a8d12de29465/im-fine-stkd-rev-gold.jpg",
	    					true, Time.getTimeNow(), questionRepository.findById(2l).get(), "birduser", "catuser"),
	    			
	    			new Answer (null, 0, new ArrayList<AnswerVote>(), "How About You?", null, true, 
	    					Time.getTimeNow(), questionRepository.findById(2l).get(), "birduser", "catuser"),
	    			
	    			new Answer (null, 0, new ArrayList<AnswerVote>(), "I am not okay", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmR80fYZfKffpeV_ZiuIMGzu_nQZcCHwGjAh_wFCpJgA&usqp=CAU&ec=48600112",
	    					true, Time.getTimeNow(), questionRepository.findById(2l).get(), "doguser", "birduser"),
	    			
	    			new Answer (null, 0, new ArrayList<AnswerVote>(), "Why?", null, false, 
	    					Time.getTimeNow(), questionRepository.findById(2l).get(), "doguser", null),
	    			
	    			new Answer (null, 0, new ArrayList<AnswerVote>(), "White Rice", null, true, 
	    					Time.getTimeNow(), questionRepository.findById(3l).get(), "birduser", "catuser")
	    			
	    			).collect(Collectors.toList());
	    	answerRepository.saveAll(answers);
	    }
	    
	    @PostConstruct
	    public void initChat() {
	    	List<Chat> chats = Stream.of(
	    			new Chat(null, "birduser", "doguser", new ArrayList<Message>()),
	    			new Chat(null, "birduser", "catuser", new ArrayList<Message>()),
	    			new Chat(null, "bearuser", "birduser", new ArrayList<Message>()),
	    			new Chat(null, "birduser", "penguinuser", new ArrayList<Message>())
	    			).collect(Collectors.toList());
	    	chatRepository.saveAll(chats);
	    	
	    	List<Message> messages = Stream.of(
	    			new Message(null, Time.getTimeNow(), "Hello what are you doing?", "bearuser", chatRepository.findById(3l).get()),
	    			new Message(null, Time.getTimeNow(), "I am eating", "birduser", chatRepository.findById(3l).get()),
	    			new Message(null, Time.getTimeNow(), "Eating What?", "bearuser", chatRepository.findById(3l).get()),
	    			new Message(null, Time.getTimeNow(), "Nuts", "birduser", chatRepository.findById(3l).get()),
	    			new Message(null, Time.getTimeNow(), "Chirp Chirp", "birduser", chatRepository.findById(2l).get())
	    			).collect(Collectors.toList());
	    	messageRepository.saveAll(messages);
	    }
	    	

	public static void main(String[] args) {
		System.setProperty("server.port", "9192");
		SpringApplication.run(SpringbootJwtSecurityApplication.class, args);
	}

}
