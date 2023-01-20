package application.controllers.watch.controller;

import application.modal.users.user.Viewer;

import java.util.Objects;

public interface Factory {
    WatchPageController createFactory(Viewer viewer);
}
