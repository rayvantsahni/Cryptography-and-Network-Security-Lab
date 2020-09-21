#include <iostream>

using namespace std;

int main()
{
    string password = "incorrect";
    string username = "coolboyy";
    
    string entered_user, entered_pd;
    
    cout << "enter username  \n";
    cin >> entered_user;
    
    cout << "enter password  \n";
    cin >> entered_pd;
    
    
    if (username.size() != entered_user.size())
    {
        cout << "wrong username\n";
    }
    else
    {
        for (int i = 0; i < username.size(); i++)
        {
            if (username[i] != entered_user[i])
            {
                cout << "wrong username\n";
                break;
            }
        }
        cout << "correct username\n";
    }
    
    
    if (password.size() != entered_pd.size())
    {
        cout << "wrong password\n";
    }
    else
    {
        for (int i = 0; i < password.size(); i++)
        {
            if (password[i] != entered_pd[i])
            {
                cout << "wrong password\n";
                break;
            }
        }
        cout << "correct password\n";
    }
    
    
}
