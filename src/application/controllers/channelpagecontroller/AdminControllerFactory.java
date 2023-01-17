package application.controllers.channelpagecontroller;

public class AdminControllerFactory implements ChannelControllerFactory{
    @Override
    public ChannelPageController createController() {
        return new ChannelPageController() {
            @Override
            public void updateView() {
                super.updateView();
                getChannelPage().displayMonetizationOption();
                getChannelPage().displayDeleteOption();
            }
        };
    }
}
