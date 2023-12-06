# TestNG_Selenium_Baidu
New  show case sample of baidu image searching by using selenium &amp; testng frameworks

# Differences to former google search 
1. No need to use VPN from mainland IP which leads to less bugs caused by webdriver or network issues.
2. Instead of using simple assertion to compare two pictures by their content / tag / keywords attribute properties in former google search sample, I was unable to collect information from baidu picture search result page. So I made a bypass: download search result picture to local disk and compare them locally, and set file compare measurement parameter to 0.1. However this comparation method is very basic, it's nethier good nor accuracy.

# How to run test
1. prepare a picture name "test1.png" in local disk path D:\test1.png   Or  prepare any picture file and change the value of parameter "uploadImagePath" in code file "src/main/data/configResultIndex.xml" to your prepared picture files name.
2. click mouse right button on file "src/main/data/configResultIndex.xml" and select run the whole test suite
