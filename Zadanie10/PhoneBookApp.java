package org.example;

public class PhoneBookApp {
    public static void main(String[] args) {
        PhoneBookModel model = new PhoneBookModel();
        PhoneBookView view = new PhoneBookView();
        PhoneBookController controller = new PhoneBookController(model, view);
        view.run();
    }
}
