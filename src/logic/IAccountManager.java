package logic;

import entities.User;

public interface IAccountManager {
	public User signUp(String username, String password, String password2, String email) throws AppException;

	public void delete(String username, String password) throws AppException;

	public void update(String username, String password, String password2, String email) throws AppException;

	public User login(String username, String password) throws AppException;

	public void logout() throws AppException;
}
