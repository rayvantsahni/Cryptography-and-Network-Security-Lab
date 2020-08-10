#include <iostream>

using namespace std;

int main()
{
    string username = "coolboyy";
    
    string entered_pd;
    
    cout << "enter password  \n";
    cin >> entered_pd;
    
    cout << "username: " << username << endl;
    cout << "password: ";
    
    for (int i = 0; i < entered_pd.size(); i++)
    {
        cout << '*';
    }
    
    
}
