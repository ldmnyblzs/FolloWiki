package logic;

import entities.User;

public interface IAccountManager{
	public User signUp(String username, String password, String password2, String email) throws UserException;
	public void deleteAcc(String username, String password) throws UserException;
	public void update(String username, String password, String password2, String email) throws UserException;
	public User login(String username, String password) throws UserException;
	public void logout() throws UserException;
}
