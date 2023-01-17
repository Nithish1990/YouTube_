package application.controllers.channelpagecontroller;

public class ContentCreatorControllerFactory implements ChannelControllerFactory{

    @Override
    public ChannelPageController createController() {
        return new ChannelPageController() {
            @Override
            public void updateView() {
                super.updateView();
                getChannelPage().displayUploadButton();
            }
        };
    }
}
