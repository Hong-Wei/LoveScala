# 1 support html tags
This is a regular paragraph.

<table>
    <tr>
        <td>Foo</td>
    </tr>
</table>

This is another regular paragraph.


# 2 special characters
< -- &lt;  
& -- &amp;  
© -- &copy;  

AT&T -- AT&amp;T help you translate
4 < 5   help you translate

http://images.google.com/images?num=30&q=larry+bird  
http://images.google.com/images?num=30&amp;q=larry+bird


#3 Headers
# This is an H1
## This is an H2
###### This is an H6


#4 blockquotes

##4.1 > same 
> This is a blockquote with two paragraphs. Lorem ipsum dolor sit amet,
> consectetuer adipiscing elit. Aliquam hendrerit mi posuere lectus.
> Vestibulum enim wisi, viverra nec, fringilla in, laoreet vitae, risus.
> 
> Donec sit amet nisl. Aliquam semper ipsum sit amet velit. Suspendisse
> id sem consectetuer libero luctus adipiscing.


> This is a blockquote with two paragraphs. Lorem ipsum dolor sit amet,
consectetuer adipiscing elit. Aliquam hendrerit mi posuere lectus.
Vestibulum enim wisi, viverra nec, fringilla in, laoreet vitae, risus.

> Donec sit amet nisl. Aliquam semper ipsum sit amet velit. Suspendisse
id sem consectetuer libero luctus adipiscing.

##4.2 nest quotes
> This is the first level of quoting.
>
> > This is nested blockquote.
>
> Back to the first level.

##4.3 Blockquotes can contain other Markdown elements, including headers, lists, and code blocks
> ###### This is a header.
> 
> 1.   This is the first list item.
> 2.   This is the second list item.
> 
> Here's some example code:
> 
>     return shell_exec("echo $input | $markdown_script");


#5 Lists
##5.1 unordered lists
*   Red
*   Green
*   Blue

+   Red
+   Green
+   Blue

-   Red
-   Green
-   Blue

##5.2 Ordered lists
1.  Bird
2.  McHale
3.  Parish


#6 Code Blocks
#6.1  at least 4 spaces or 1 tab

This is a normal paragraph:

    This is a code block.


#7 Span Elements

This is [an example](http://example.com/ "Title") inline link.

[This link](http://example.net/) has no title attribute.

See my [About](/about/) page for details.   


This is [an example][id] reference-style link.  
This is [an example] [id] reference-style link.

[id]: http://example.com/  "Optional Title Here" 


#8 Emphasis

*single asterisks*

_single underscores_

**double asterisks**

__double underscores__

\*this text is surrounded by literal asterisks\*


#9 Code

Use the `printf()` function.  
``There is a literal backtick (`) here.``

A single backtick in a code span: `` ` ``

A backtick-delimited string in a code span: `` `foo` ``

Please don't use any `<blink>` tags.



#10 Images
![Alt text](/path/to/img.jpg)

![Alt text](/path/to/img.jpg "Optional title")




#11 Miscellaneous
<http://example.com/> 

<address@example.com>


#12 Backslash Escapes

\*literal asterisks\*

\   backslash  
`   backtick  
*   asterisk  
_   underscore  
{}  curly braces  
[]  square brackets  
()  parentheses  
#   hash mark  
+   plus sign  
-   minus sign (hyphen)  
.   dot  
!   exclamation mark  



