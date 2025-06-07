    .data
buffer:          .byte 'Hello, ________________________'
question:         .byte 'What is your name?\n\0'
input_addr:      .word 0x80
output_addr:    .word 0x84

    .text
output_cstring:
  mv      t0, a0
  addi  t3, zero, 0x00

  lw      t1, 0(t0)        
  and   t1, t1, a2  

output_while:

  lw      t2, 0(t0)        
  and   t2, t2, a2

  beq    t2, t3, ret

    sb      t2, 0(a1)
    addi    t0, t0, 1

    j     output_while
ret:
  jr    ra

hello_user_cstr:
  addi  sp, sp, -4
  sw    ra, 0(sp)    
      
  addi   a2,  zero, 0xFF      
  
print_question:
  lui     a0, %hi(question)
    addi    a0, a0, %lo(question)
  
  jal    ra, output_cstring

get_user_input:
  lui     t3, %hi(input_addr)
    addi    t3, t3, %lo(input_addr)
  lw      t3, 0(t3)
  
  lui     t1, %hi(buffer)
    addi    t1, t1, %lo(buffer)
  addi    t1, t1, 7 

while_user_input:
  lw      t2, 0(t3)    
  and   t2, t2, a2
  
  addi   t5, t2, -10
  beqz  t5, store_exclamation_point 
  
    sb      t2, 0(t1)
    addi    t1, t1, 1    
  addi   t5, t1, -31
  beqz  t5, return_overflow

    j     while_user_input

store_exclamation_point:  
  addi  t2, zero, '!'
  sb    t2, 0(t1)
  addi  t2, zero, 0x00
  addi  t1, t1, 1
  sb    t2, 0(t1)

print_buffer:  
  lui     a0, %hi(buffer)
    addi    a0, a0, %lo(buffer)

  jal    ra, output_cstring
  lw    ra, 0(sp)
  addi    sp, sp, 4
  jr     ra 
  
return_overflow:
  lui     t2, %hi(0xCCCC_CCCC)
    addi    t2, t2, %lo(0xCCCC_CCCC)
  sw    t2, 0(a1)
  lw    ra, 0(sp)
  addi    sp, sp, 4
  jr    ra

  
    .org     0x88
_start:
  lui     sp, %hi(0x1000)                
    addi    sp, sp, %lo(0x1000)
  
  lui     a1, %hi(output_addr)
    addi    a1, a1, %lo(output_addr)
  lw      a1, 0(a1)          
  
  jal   ra, hello_user_cstr
  halt
