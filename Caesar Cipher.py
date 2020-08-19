def encrypt(plaintext, key):
    encrypted_list = []
    for char in plaintext:
        if ord(char) > 96 and ord(char) < 123: #for lowercase characters
            encrypted_list.append(chr((((ord(char) + key) - 97) % 26) + 97))
        elif ord(char) > 64 and ord(char) < 91: #for uppercase characters
            encrypted_list.append(chr((((ord(char) + key) - 65) % 26) + 65))
        else:
            encrypted_list.append(char)

    return "".join(encrypted_list)

def decrypt(ciphertext, key):
    return encrypt(ciphertext, 26 - key)

if __name__ == "__main__":

    message = "Bye bYE WorLd!!"
    key = 3
    print("Original Message: {}".format(message), end = "\n\n")

    encoded_message = encrypt(message, key)
    decoded_message = decrypt(encoded_message, key)
    print("Coded Message: {}".format(encoded_message))
    print("Decoded Message: {}".format(decoded_message))