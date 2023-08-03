package com.cravin.marketplace;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller Class to controll navigation.  Handles all 'GetMapping'
 * and 'PostMapping' requests and maps the url to the HTML page.
 */

@Controller
public class navController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    
    private int numUsers; // counts number of users to serve as ID
    private User currUser; // Stores the logged in User

    public navController() {
        this.numUsers = 1;
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("user", currUser);
        return "index.html";
    }

    @GetMapping("/orderHistory")
    public String orderHistory(Model model) {
        if (currUser == null)
            return "LogIn.html";
        model.addAttribute("user", currUser);
        List<Order> orderList;
        if (currUser.getIsAdmin() == true) {
            orderList = orderRepository.findAll();
        } else {
            orderList = orderRepository.findByCustomerID(currUser.getId());
        }
        model.addAttribute("orderList", orderList);
        return "orderHistory.html";
    }

    @GetMapping("/orderDetails/{id}")
    public String orderDetails(@PathVariable String id, Model model) {
        if (currUser == null)
            return "LogIn.html";
        model.addAttribute("user", currUser);
        Order order = orderRepository.findById(id).get();
        model.addAttribute("order", order);
        List<PurchasedModel> purchased = purchaseRepository.findByOrderID(order.getOrderId());
        model.addAttribute("purchased", purchased);
        return "orderDetails.html";
    }

    @PostMapping("/orderDetails{id}")
    public @ResponseBody void changeStatus(@RequestParam String id, @RequestParam String status, Model model,
            HttpServletResponse response) {
        orderRepository.updateStatus(id, status);
        try {
            response.sendRedirect("/orderHistory");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/ViewUsers")
    public String viewUsers(Model model) {
        if (currUser == null) return "LogIn.html";
        model.addAttribute("user", currUser);
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "viewUsers.html";
    }

    @PostMapping("/EditModelDetails")
    public @ResponseBody void changeModelDetails(
            @RequestParam double price,
            @RequestParam String description,
            @RequestParam String hidden,
            @RequestParam int id,
            Model model,
            HttpServletResponse response) {
        modelRepository.updateDescription(id, description);
        modelRepository.updatePrice(id, price);

        modelRepository.updateVisibility(id, Boolean.parseBoolean(hidden));
        try {
            response.sendRedirect("/ViewModels");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/LogIn")
    public String login() {
        // add code here to handle if the user is already logged in
        if (currUser != null)
            return "login_success.html";
        return "LogIn.html";
    }

    @GetMapping("/ViewModels")
    public String viewmodels(Model model) {
        if (currUser == null)
            return "LogIn.html";
        model.addAttribute("user", currUser);
        List<AiModel> aiModels;
        // update list differently if user is admin
        if (currUser.getIsAdmin())
            aiModels = modelRepository.findAll();
        else
            aiModels = modelRepository.getVisible(false);

        model.addAttribute("listOfModels", aiModels);
        return "ViewModels.html";
    }

    @GetMapping("/AddModel")
    public String addModels(Model model) {
        if (currUser == null)
            return "LogIn.html";
        model.addAttribute("user", currUser);
        return "AddModel.html";
    }

    @PostMapping("/AddModel")
    public @ResponseBody void addModel(AiModel ai, Model model, HttpServletResponse response) {
        ai.setInCart(false);
        ai.setTrained(false);
        long numModels = modelRepository.count();
        String imgName;
        if (numModels > 16) imgName = "/img1.png";
        else imgName = "/img" + numModels + ".png";
        ai.setImgName(imgName);
        modelRepository.save(ai);

        try {
            response.sendRedirect("/ViewModels");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @GetMapping("/ModelDetails/{id}")
    public String modelDetails(@PathVariable int id, Model model) {
        if (currUser == null)
            return "LogIn.html";
        model.addAttribute("user", currUser);
        AiModel selectedModel = modelRepository.findById((long) id).get();
        model.addAttribute("selectedModel", selectedModel);
        return "ModelDetails.html";
    }

    @GetMapping("/EditModelDetails/{id}")
    public String editModelDetails(@PathVariable int id, Model model) {
        if (currUser == null)
            return "LogIn.html";
        model.addAttribute("user", currUser);
        AiModel selectedModel = modelRepository.findById((long) id).get();
        model.addAttribute("model", selectedModel);
        return "EditModelDetails.html";
    }

    @PostMapping("/ModelDetails")
    public void addToCart(@RequestParam int modelId, Model model, HttpServletResponse response) {
        AiModel selectedModel = modelRepository.findById((long) modelId).get();
        selectedModel.setInCart(true);
        modelRepository.save(selectedModel);

        try {
            response.sendRedirect("/ViewModels");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/ShoppingCart")
    public String shoppingcart(Model model) {
        model.addAttribute("user", currUser);
        if (currUser == null)
            return "LogIn.html";
        List<AiModel> modelShoppingCart = modelRepository.findByInCart(true);
        double totalPrice = 0;
        for (AiModel a : modelShoppingCart) {
            totalPrice += a.getPrice();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("filledShoppingCart", modelShoppingCart);
        return "ShoppingCart.html";
    }

    @GetMapping("/checkOut")
    public String checkOut(Model model) {
        model.addAttribute("user", currUser);
        if (currUser == null)
            return "LogIn.html";
        List<AiModel> modelShoppingCart = modelRepository.findByInCart(true);
        double price = 0;
        for (AiModel modelCart : modelShoppingCart) {
            price += modelCart.getPrice();
        }
        model.addAttribute("totalPrice", price);
        return "checkOut.html";
    }

    @GetMapping("checkOutSuccessful")
    public String checkOutSuccess() {
        // once the user has clicked 'check out' we need to store that order in the db
        createOrder();
        return "checkOutSuccessful.html";
    }

    private void createOrder() {
        // get places where 'in cart == true' and add to new order object
        Order order = new Order();
        String uniqueID = UUID.randomUUID().toString();
        order.setOrderId(uniqueID);
        order.setCustomerID(currUser.getId());
        order.setOrderState("new");

        double orderPrice = 0;
        List<AiModel> modelShoppingCart = modelRepository.findByInCart(true);
        for (AiModel m : modelShoppingCart) {
            // add to order
            PurchasedModel pm = new PurchasedModel(order.getOrderId(), m.getModelName(), m.getTrained(), m.getPrice());
            purchaseRepository.save(pm);
            System.out.println(pm);

            orderPrice += m.getPrice();
        }
        order.setOrderPrice(orderPrice);
        // add order to database
        orderRepository.save(order);
        modelRepository.updateCart();
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        user.setId(numUsers);
        if ((user.getUsername().equals("admin")) && (user.getPassword().equals("adminpass")))
            user.setIsAdmin(true);
        else
            user.setIsAdmin(false);
        userRepository.save(user);
        for (User u : userRepository.findAll()) {
            System.out.println(u.toString());
        }
        numUsers++;
        return "register_success";
    }

    @PostMapping("/process_login")
    public String processlogin(String username, String password, Model model, HttpServletResponse response) {
        User u = userRepository.findByUsername(username);
        if (u != null) {
            if (u.getPassword().equals(password)) {
                this.currUser = u;
                try {
                    response.sendRedirect("/");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "LogIn.html";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        this.currUser = null;
        return "index.html";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model){
        model.addAttribute("user", currUser);
        return "changePassword.html";
    }

    @PostMapping("/changePassword")
    public @ResponseBody String changePasswordSuccess(HttpServletResponse response, String username, String oldPass, String newPass, Model model){
        User u = userRepository.findByUsername(username);
        if (u != null) {
            if (u.getPassword().equals(oldPass)) {
                try {
                    userRepository.updatePassword(username, newPass);
                    response.sendRedirect("/");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "changePassword.html";
    }      

    @GetMapping("/refund")
    public String refund(Model model){
        return "refund.html";
    }

    @PostMapping("/refund")
    public void refundSuccess(HttpServletResponse response, String id){
        orderRepository.deleteById(id);
        try {
            response.sendRedirect("/refund");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
