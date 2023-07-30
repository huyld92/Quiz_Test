/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.controller;

import fa.group.mock.entity.Answer;
import fa.group.mock.entity.Question;
import fa.group.mock.entity.Quiz;
import fa.group.mock.entity.Result;
import fa.group.mock.entity.Test;
import fa.group.mock.entity.Topic;
import fa.group.mock.entity.User;
import fa.group.mock.forms.UserForm;
import fa.group.mock.services.AnswerService;
import fa.group.mock.services.QuestionService;
import fa.group.mock.services.QuizService;
import fa.group.mock.services.ResultService;
import fa.group.mock.services.TestService;
import fa.group.mock.services.TopicService;
import fa.group.mock.services.UserService;
import fa.group.mock.utils.UserValidator;
import fa.group.mock.utils.Utils;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Duc Huy
 */
@Controller
public class MainController {

    @Autowired
    QuizService quizServices;

    @Autowired
    TopicService topicServices;

    @Autowired
    TestService testService;
    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerService answerService;
    @Autowired
    ResultService resultService;
    @Autowired
    private UserValidator userValidator;

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form mục tiêu
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == UserForm.class) {
            dataBinder.setValidator(userValidator);
        }

    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String registerPage(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "registerPage";
    }

    // Phương thức này được gọi để lưu thông tin đăng ký.
    // @Validated: Để đảm bảo rằng Form này
    // đã được Validate trước khi phương thức này được gọi.
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveRegister(Model model,
            @ModelAttribute("userForm") @Validated UserForm userForm,
            BindingResult bindingResult) {

        // Validate result
        if (bindingResult.hasErrors()) {
            return "registerPage";
        }
        User newUser;
        try {
            newUser = userService.createUser(userForm);
            if (newUser != null) {
                model.addAttribute("registerSuccess", "Registered successfully!");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registerPage";
        }

        return "loginPage";
    }

    @RequestMapping(value = {"/login"})
    public String loginPage() {
        return "loginPage";
    }

    @RequestMapping("/errorPage")
    public String errorPage(Model model) {
        model.addAttribute("messageError", "This page does not support your role!");
        return "errorPage";
    }

    @RequestMapping(value = {"/", "/home-page"})
    public String indexPage(Model model) {
        List<Topic> listTopic = topicServices.listAllTopic();
        model.addAttribute("listTopic", listTopic);
        return "index";
    }

    //Quiz Controller
    @RequestMapping(value = {"/quiz/createPage"})
    public String createQuizPage(Model model) {
        model.addAttribute("quiz", new Quiz());
        List<Topic> list = topicServices.listAllTopic();
        if (list != null) {
            model.addAttribute("listTopic", list);
        }
        return "createQuizPage";
    }

    @RequestMapping(value = {"/quiz/search"})
    public String searchQuizByName(@RequestParam("searchValue") String searchValue, Model model) {
        if (searchValue.trim().isEmpty()) {
            return indexPage(model);
        }
        List<Quiz> listQuiz = quizServices.searchByTitle(searchValue);
        if (listQuiz != null && !listQuiz.isEmpty()) {
            model.addAttribute("listQuiz", listQuiz);
        } else {
            model.addAttribute("messageError", "No quiz matches search value!");
        }
        return "searchResultPage";
    }

    @RequestMapping(value = {"/quiz/create"})
    public String createQuiz(@ModelAttribute("quiz") @Validated Quiz quiz,
            BindingResult bindingResult, Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            List<Topic> listTopic = topicServices.listAllTopic();
            model.addAttribute("messageError", "Added failled!");
            model.addAttribute("listTopic", listTopic);
            return "createQuizPage";
        } else {
            boolean checkEmpty = true;

            if (quiz.getQuizTitle().trim().isEmpty()) {
                checkEmpty = false;
            }
            if(quiz.getTopic() == null){
                checkEmpty = false;
            }
            if (checkEmpty) {
                User user = userService.getUserByUsername(principal.getName());
                quiz.setUser(user);
                Quiz saved = quizServices.save(quiz);
                if (saved != null) {
                    model.addAttribute("message", "Added successful!");
                    return quizView(quiz.getQuizID(), model, principal);
                } else {
                    model.addAttribute("messageError", "Added failled!");
                }
            } else {
                model.addAttribute("messageError", "Cannot empty any fields");
            }
        }
        return createQuizPage(model);
    }

    @RequestMapping("/quizView/{quizID}")
    public String quizView(@PathVariable(name = "quizID") Integer quizID,
            Model model, Principal principal) {
        Quiz quiz = quizServices.getById(quizID);
        // lấy username hiện tại
        String username = principal.getName();
        if (username.equals(quiz.getUser().getUsername())) {
            model.addAttribute("isOwner", true);
        }
        List<Topic> listTopic = topicServices.listAllTopic();
        model.addAttribute("quiz", quiz);
        model.addAttribute("listTopic", listTopic);
        return "quizViewPage";
    }

    @RequestMapping("/quiz/myQuiz")
    public String myQuiz(Principal principal, Model model) {
        User user = userService.getUserByUsername(principal.getName());
        List<Quiz> listQuiz = quizServices.findAllByUserID(user.getUserID());
        model.addAttribute("listQuiz", listQuiz);
        return "myQuizPage";
    }

    @RequestMapping("/quiz/delete/{quizID}")
    public String deleteQuiz(@PathVariable(name = "quizID") int quizID, Model model) {
        quizServices.deleteQuiz(quizID);
        return indexPage(model);
    }

    @RequestMapping("copyQuiz/{quizID}")
    public String createCloneQuiz(@PathVariable(name = "quizID") int quizID,
            Model model, Principal principal) {
//        User user = userService.getUserByUsername(principal.getName());
//        Quiz quiz = quizServices.getById(quizID);
        //NOt yet
        return indexPage(model);
    }

    @RequestMapping("/quiz/edit")
    public String updateQuiz(@ModelAttribute("quiz") @Validated Quiz quiz,
            BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            //Error 
        } else {
            try {
                quizServices.update(quiz);
            } catch (Exception e) {
                model.addAttribute("messageError", "Error edit quiz!");
            }
        }
        return quizView(quiz.getQuizID(), model, principal);
    }

    // Question Controller
    @RequestMapping("/question/editPage/{questionID}")
    public String editQuestionPage(@PathVariable(name = "questionID") Integer questionID,
            Model model) {
        Question question = questionService.getByID(questionID);
        model.addAttribute("question", question);
        return "editQuestionPage";
    }

    @RequestMapping("/question/edit")
    public String editQuestion(@ModelAttribute("question") @Validated Question question,
            BindingResult bindingResult, @RequestParam("isCorrect") String indexCorrect,
            Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
        } else {
            boolean checkEmpty = true;
            if (question.getAnswers() == null || question.getAnswers().isEmpty()) {
                checkEmpty = false;
            } else {
                for (Answer answer : question.getAnswers()) {
                    String answerText = answer.getAnswerText().trim();
                    if (!answerText.isEmpty()) {
                        answer.setAnswerText(answerText);
                        answer.setCorrect(false);
                        answer.setQuestion(question);
                    } else {
                        checkEmpty = false;
                        break;
                    }
                }
                try {
                    int index = Integer.parseInt(indexCorrect);
                    question.getAnswers().get(index).setCorrect(true);
                } catch (NumberFormatException e) {
                    checkEmpty = false;
                }
            }

            if (checkEmpty) {
                questionService.update(question);
                answerService.update(question.getAnswers());
            } else {
                model.addAttribute("messageError", "Cannot empty any fields");
                return createQuestionPage(question.getQuestionID(), model);
            }
        }
        return quizView(question.getQuiz().getQuizID(), model, principal);
    }

    @RequestMapping(value = {"/question/createPage/{quizID}"})
    public String createQuestionPage(@PathVariable(name = "quizID") Integer quizID, Model model) {

        Quiz quiz = quizServices.getById(quizID);

        Question question = new Question();
        question.setQuiz(quiz);
        ArrayList<Answer> listAnswer = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Answer answer = new Answer();
            answer.setQuestion(question);
            listAnswer.add(answer);
        }
        question.setAnswers(listAnswer);
        model.addAttribute("question", question);
        return "createQuestionPage";
    }

    @RequestMapping(value = {"/question/create"})
    public String createQuestion(@ModelAttribute("question") @Validated Question question,
            BindingResult bindingResult, @RequestParam("isCorrect") String indexCorrect,
            Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            // ERROR
        } else {
            boolean checkEmpty = true;
            if (question.getAnswers() == null || question.getAnswers().isEmpty()) {
                checkEmpty = false;
            } else {
                try {
                    int index = Integer.parseInt(indexCorrect);
                    question.getAnswers().get(index).setCorrect(true);
                } catch (NumberFormatException e) {
                    checkEmpty = false;
                }
                for (Answer answer : question.getAnswers()) {
                    String answerText = answer.getAnswerText().trim();
                    if (!answerText.isEmpty()) {
                        answer.setAnswerText(answerText);
                        answer.setQuestion(question);
                    } else {
                        checkEmpty = false;
                    }
                }
            }

            if (checkEmpty) {
                int totalQuestion = question.getQuiz().getTotalQuestion() + 1;
                question.getQuiz().setTotalQuestion(totalQuestion);
                Question saved = questionService.save(question);
                if (saved != null) {
                    return quizView(saved.getQuiz().getQuizID(), model, principal);
                } else {
                    model.addAttribute("messageError", "Added failled!");
                }
            } else {
                model.addAttribute("messageError", "Cannot empty any fields");
            }
        }
        return createQuestionPage(question.getQuiz().getQuizID(), model);
    }

    @RequestMapping("/question/delete/{quizID}/{questionID}")
    public String deleteQuestion(@PathVariable(name = "quizID") Integer quizID,
            @PathVariable(name = "questionID") Integer questionID, Model model,
            Principal principal) {
        questionService.deleteQuestionById(questionID);
        Quiz quiz = quizServices.getById(quizID);
        int totalQuestion = quiz.getTotalQuestion() - 1;
        quizServices.updateTotalQuestion(totalQuestion, quizID);
        return quizView(quizID, model, principal);
    }

    // Test Controller
    @RequestMapping(value = {"test/createTestPage/{quizID}"})
    public String createTestPage(@PathVariable(name = "quizID") Integer quizID,
            Model model, Principal principal) {
//        User user = userService.getUserByUsername(principal.getName());
        Quiz quiz = quizServices.getById(quizID);
//        if (quiz.getUser().getUserID() != user.getUserID()) {
//            // tạo clone quiz
//        }
        Test test = new Test();
        test.setQuiz(quiz);
        model.addAttribute("test", test);

        return "createTestPage";
    }

    @RequestMapping(value = {"test/create"})
    public String createTest(@ModelAttribute("test") @Validated Test test,
            BindingResult bindingResult, @RequestParam(name = "paramStartTime") String paramStartTime,
            @RequestParam(name = "paramEndTime") String paramEndTime, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            System.out.println("ERROR Model! " + bindingResult.toString());
        } else {
            boolean checkEmpty = true;
            if (test.getTestTitle().trim().isEmpty()) {
                checkEmpty = false;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date startTime = null, endTime = null;
            try {
                startTime = sdf.parse(paramStartTime.replace("T", " "));
                endTime = sdf.parse(paramEndTime.replace("T", " "));

                if (startTime.after(endTime)) {
                    checkEmpty = false;
                    model.addAttribute("timeError", "Start time must be before the end time!");
                }
            } catch (ParseException ex) {
                checkEmpty = false;
                model.addAttribute("timeError", "Invalid time value!");
            }

            if (checkEmpty) {
                User user = userService.getUserByUsername(principal.getName());
                test.setUserID(user.getUserID());
                test.setStartTime(startTime);
                test.setEndTime(endTime);
                boolean exist;
                do {
                    String joinCode = Utils.randomStringDigital(8);
                    //kiểm tra tồn tại joinCoce
                    exist = testService.existJoinCode(joinCode);
                    if (!exist) {
                        test.setJoinCode(joinCode);
                    }
                } while (exist);

                Test saved = testService.save(test);
                if (saved != null) {
                    model.addAttribute("listTest", saved);
                    return quizView(saved.getQuiz().getQuizID(), model, principal);
                } else {
                    model.addAttribute("messageError", "Added failled!");
                }
            } else {
                model.addAttribute("test", test);
                model.addAttribute("messageError", "Cannot empty any fields");
            }
        }
        return "createTestPage";
    }

    @RequestMapping(value = {"test/joinTest"})
    public String joinTest(@RequestParam(name = "joinCode") String joinCode,
            Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        // kiểm tra mã tham gia Test
        Test test = testService.joinTest(joinCode);
        // kiểm tra người dùng đã làm bài trước đó chưa
        if (test != null) {
            Result result = resultService.exists(test.getTestID(), user.getUserID());
            if (result == null) {
                // chưa làm
                test.setNumOfParticipants(test.getNumOfParticipants() + 1);
                testService.save(test);
                // lấy thời gian thực
                Timestamp timestamp = new Timestamp(System.currentTimeMillis() + test.getQuiz().getTotalTime());
                result = new Result(user.getUserID(), test, timestamp);
                Result resultSaved = resultService.save(result);
                model.addAttribute("resultID", resultSaved.getResultID());
                model.addAttribute("question", test.getQuiz().getListQuestion().get(0));
                model.addAttribute("count", 1);
                model.addAttribute("timeDown", test.getQuiz().getTotalTime() + ":00");
                return "testViewPage";
            } else {
                model.addAttribute("messageError", "You have participated in the assignment!");
                return "/errors/errorPage";
            }
        } else {
            model.addAttribute("joinCodeError", "Join Code is invalid!");
        }
        return indexPage(model);
    }

    // danh sách báo số người tham gia bài test
    @RequestMapping(value = {"/test/next"})
    public String nextQuestoin(@RequestParam(name = "resultID") Integer resultID,
            @RequestParam(name = "count") Integer count,
            @RequestParam(name = "indexAnswer") Integer indexAnswer,
            @RequestParam(name = "timeDown") String timeDown,
            Model model, Principal principal) {
        Result result = resultService.getById(resultID);
        List<Question> listQuestion = result.getTest().getQuiz().getListQuestion();
        if (indexAnswer != null) {
            Answer answer = listQuestion.get(count - 1).getAnswers().get(indexAnswer);
            result.getAnswer().add(answer);
            if (answer.isCorrect()) {
                int countCorrect = result.getCountCorrect() + 1;
                result.setCountCorrect(countCorrect);
            }
        }

        boolean lastQuestion = count >= listQuestion.size();
        if (lastQuestion || timeDown.equals("0:00")) {
            result.setTotaMark((10 / count) * result.getCountCorrect());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            result.setSubmitTime(timestamp);
            result.setStatusResult(true);
            resultService.save(result);
        } else {
            resultService.save(result);
            model.addAttribute("resultID", result.getResultID());
            model.addAttribute("question", listQuestion.get(count++));
            model.addAttribute("count", count);
            model.addAttribute("timeDown", timeDown);
            return "testViewPage";
        }
        return markReport(model, principal);
    }

    @RequestMapping(value = {"/reportTest/{testID}"})
    public String reportTest(@PathVariable(name = "testID") Integer testID,
            Model model, Principal principal) {
        return "#";
    }

    // danh sách các bài test mà một User đã tạo
    @RequestMapping(value = {"/myTest"})
    public String listTest(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<Test> list = testService.listAllByUserID(user.getUserID());
        if (list == null || list.isEmpty()) {
            model.addAttribute("messageError", "you haven't created any test before!");
        } else {
            model.addAttribute("listTest", list);
        }
        return "listTestPage";
    }

    // Result Controller
    @RequestMapping(value = {"/markReport"})
    public String markReport(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        // danh sách báo cáo điểm từ những bài test đã làm 
        List<Result> list = resultService.listAllByUserID(user.getUserID());
        if (list == null || list.isEmpty()) {
            model.addAttribute("messageError", "you haven't join any test before!");
        } else {
            model.addAttribute("listResult", list);
        }
        return "markReportPage";
    }
}
