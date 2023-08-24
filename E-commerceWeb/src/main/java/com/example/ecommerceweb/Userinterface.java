package com.example.ecommerceweb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Userinterface {

    GridPane logInPage;
    HBox headerBar;
    HBox footerBar;
    Button signInButton;
    Label welcomeLable;
    VBox body;
    Customer loggedInCustomer;
    ProductList productList=new ProductList();
    VBox productPage;
    Button placeOrderButton = new Button("Place Order");
    ObservableList<Product> itemInCart = FXCollections.observableArrayList();

     public BorderPane createContent(){
        BorderPane root= new BorderPane();
        root.setPrefSize(800,600);
       // root.getChildren().add(logInPage);  // method to add node as children to BorderPane
         root.setTop(headerBar);
         //root.setCenter(logInPage);
         body=new VBox();
         body.setPadding(new Insets(10));
         body.setAlignment(Pos.CENTER);
         root.setCenter(body);
         productPage=productList.getAllProduct();
         body.getChildren().add(productPage);

         root.setBottom(footerBar);

        return root;
    }
    public Userinterface(){
         createLoginPage();
         createHeaderBar();
         createFooterBar();
    }


    private void createLoginPage(){
         Text userNameText=new Text("User Name");
         Text passwordText=new Text("Password");

        TextField userName=new TextField("rkdhakad82@gmail.com");
        userName.setPromptText("Type your user name here");
        PasswordField password=new PasswordField();
        password.setText("rahul@123");
        password.setPromptText("Type your password here");

        Label messageLable=new Label("Hi");

        Button loginButton=new Button("Login");

        logInPage=new GridPane();
        logInPage.setStyle(" -fx-background-color: grey");
        logInPage.setAlignment(Pos.CENTER);
        logInPage.setHgap(10);
        logInPage.setVgap(10);
        logInPage.add(userNameText,0,0);
        logInPage.add(userName,1,0);
        logInPage.add(passwordText,0,1);
        logInPage.add(password,1,1);
        logInPage.add(messageLable,0,2);
        logInPage.add(loginButton,1,2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                String name=userName.getText();
//                messageLable.setText(name);
                String name= userName.getText();
                String pass = password.getText();
                Login login= new Login();
                loggedInCustomer= login.customerLogin(name,pass);
                if (loggedInCustomer!=null){
                    messageLable.setText("welcome : "+ loggedInCustomer.getName());
                    welcomeLable.setText("welcome-"+loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLable);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);

                }
                else {
                    messageLable.setText("LogIn Failed ! please provide correct credentials");
                }
            }
        });


    }

    private void createHeaderBar(){
         Button homeButton = new Button();
        Image image = new Image("C:\\EcommerceProject\\E-commerceWeb\\src\\HomePageButton.jpeg");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(60);
        homeButton.setGraphic(imageView);

         TextField searchBar=new TextField();
         searchBar.setPromptText("Search here");
         searchBar.setPrefWidth(280);

         Button searchButton= new Button("Search");

          signInButton=new Button("Sign In");
          welcomeLable=new Label();

          Button cartButton = new Button("Cart");

          Button orderButton = new Button("Orders");


         headerBar=new HBox();
         headerBar.setStyle(" -fx-background-color: blue");
         headerBar.setPadding(new Insets(10));
         headerBar.setSpacing(10);
         headerBar.setAlignment(Pos.CENTER);
         headerBar.getChildren().addAll(homeButton,searchBar,searchButton,signInButton,cartButton,orderButton);

         signInButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();// remove all
                 body.getChildren().add(logInPage); //put log in page
                 headerBar.getChildren().remove(signInButton);
             }
         });
         cartButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 VBox prodPage = productList.getProductInCart(itemInCart);
                 prodPage.setAlignment(Pos.CENTER);
                 prodPage.setSpacing(10);
                 prodPage.getChildren().add(placeOrderButton);
                 body.getChildren().add(prodPage);
                 footerBar.setVisible(false);// all cases need to handled for this
             }
         });

         placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 // need list of products and a customer.

                 if (itemInCart==null){
                     // please select a product frist to place order
                     showDialog("Please add some products in the Cart to place order!!");
                     return;
                 }
                 if (loggedInCustomer==null){
                     showDialog("please login frist to place order");
                     return;
                 }
                 int count = Order.placeMultipleOrder(loggedInCustomer,itemInCart);
                 if (count!=0){
                     showDialog("Order for"+count+" products placed successfully!!");
                 }
                 else {
                     showDialog("Order failed!!");
                 }
             }
         });
         homeButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 body.getChildren().add(productPage);
                 footerBar.setVisible(true);
                 if(loggedInCustomer==null && headerBar.getChildren().indexOf(signInButton)==-1){
                   //  System.out.println(headerBar.getChildren().indexOf(signInButton));

                         headerBar.getChildren().add(signInButton);

                 }
             }
         });

    }
    private void createFooterBar() {
        Button buyNowButton = new Button("BuyNow");
        Button addToCartButton = new Button("Add to Cart");
        footerBar = new HBox();
        //footerBar.setStyle(" -fx-background-color: blue");
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if (product==null){
                    // please select a product frist to place order
                    showDialog("Please select a product first to place order!!");
                    return;
                }
                if (loggedInCustomer==null){
                    showDialog("please login frist to place order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer,product);
                if (status==true){
                    showDialog("Order placed successfully!!");
                }
                else {
                    showDialog("Order failed!!");
                }
            }
        });
        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if (product==null){
                    // please select a product frist to place order
                    showDialog("Please select a product first to add it to Cart!!");
                    return;
                }
                itemInCart.add(product);
                showDialog("Selected Item has been added to the Cart successfully!!");
            }
        });

    }
    private void  showDialog(String message){
        Alert alert= new Alert((Alert.AlertType.INFORMATION));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}
