package co.edu.unbosque.view;

public class ViewFacade {
	private LoginView loginView;
	private RegisterView registerView;
	
	public ViewFacade() {
		// TODO Auto-generated constructor stub
	}
	public LoginView getLoginView() {
		if (loginView == null) {
			loginView = new LoginView();
		}
		return loginView;
	}

	public RegisterView getRegisterView() {
		if (registerView == null) {
			registerView = new RegisterView();
		}
		return registerView;
	}

	public void setRegisterView(RegisterView registerView) {
		this.registerView = registerView;
	}
	
	public void showViewLogin() {
		getLoginView().setVisible(true);
	}

	public void showViewRegister() {
		getRegisterView().setVisible(true);
	}
	public void hideViewLogin() {
		if (loginView != null) {
			loginView.setVisible(false);
		}
	}

	public void hideViewRegister() {
		if (registerView != null) {
			registerView.setVisible(false);
		}
	}
}
