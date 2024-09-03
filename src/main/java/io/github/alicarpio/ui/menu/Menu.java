package io.github.alicarpio.ui.menu;

import io.github.alicarpio.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class Menu {
    protected final UUID id;
    protected final List<MenuItem> items;
    private final String title;

    public Menu(String title) {
        this.title = title;
        this.id = Utils.generateUniqueId();
        this.items = new ArrayList<>();
    }

    public abstract Menu build();

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void show() {
        System.out.println(title);
        items.stream()
                .map(item -> item.getRowNumber() + ". " + item.getDescription())
                .forEach(System.out::println);
    }

    public boolean selectItem(int rowNumber) {
        return items.stream()
                .filter(item -> item.getRowNumber() == rowNumber)
                .findFirst()
                .stream()
                .peek(MenuItem::activate)
                .map(menuItem -> true)
                .findFirst()
                .orElse(false);
    }

    public UUID getId() {
        return id;
    }

    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
