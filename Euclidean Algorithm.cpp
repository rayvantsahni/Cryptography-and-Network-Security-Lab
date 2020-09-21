#include <iostream>

int gcd(int a, int b)
{
    return (b == 0) ? a : gcd(b, a % b);
}


int main()
{
    int x, y;
    std::cout << "Enter the 2 numbers space separated" << std::endl;
    std::cin >> x >> y;
    std::cout << "The GCD is: " << gcd(x, y) << std::endl;
}
