/*
 * Generated Sun May 31 17:36:45 PDT 2015
 */

	.section	".rodata"
	.align	4
.$$.intFmt:
	.asciz	"%d"
.$$.strFmt:
	.asciz	"%s"
.$$.strTF:
	.asciz	"false\0\0\0true"
.$$.strEndl:
	.asciz	"\n"
.$$.strArrBound:
	.asciz	"Index value of %d is outside legal range [0,%d).\n"
.$$.strNullPtr:
	.asciz	"Attempt to dereference NULL pointer.\n"

	.section	".text"
	.align	4
.$$.printBool:
	save	%sp, -96, %sp
	set	.$$.strTF, %o0
	cmp	%g0, %i0
	be	.$$.printBool2
	nop
	add	%o0, 8, %o0
.$$.printBool2:
	call	printf
	nop
	ret
	restore

.$$.arrCheck:
	save	%sp, -96, %sp
	cmp	%i0, %g0
	bl	.$$.arrCheck2
	nop
	cmp	%i0, %i1
	bge	.$$.arrCheck2
	nop
	ret
	restore
.$$.arrCheck2:
	set	.$$.strArrBound, %o0
	mov	%i0, %o1
	call	printf
	mov	%i1, %o2
	call	exit
	mov	1, %o0
	ret
	restore
	
.$$.ptrCheck:
	save	%sp, -96, %sp
	cmp	%i0, %g0
	bne	.$$.ptrCheck2
	nop
	set	.$$.strNullPtr, %o0
	call	printf
	nop
	call	exit
	mov	1, %o0
.$$.ptrCheck2:
	ret
	restore

MYS_1.MYS_1.void:
	set	SAVE.MYS_1.MYS_1.void, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
	
	! End of function MYS_1.MYS_1.void
	call	MYS_1.MYS_1.void.fini
	nop
	ret
	restore
	SAVE.MYS_1.MYS_1.void = -(92 + 0) & -8
	
MYS_1.MYS_1.void.fini:
	save	%sp, -96, %sp
	ret
	restore

MYS_1.$MYS_1.void:
	set	SAVE.MYS_1.$MYS_1.void, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
	
	! End of function MYS_1.$MYS_1.void
	call	MYS_1.$MYS_1.void.fini
	nop
	ret
	restore
	SAVE.MYS_1.$MYS_1.void = -(92 + 0) & -8
	
MYS_1.$MYS_1.void.fini:
	save	%sp, -96, %sp
	ret
	restore

	.global	fn1
fn1:
fn1.int$3$.float$2$.bool$4$:
	set	SAVE.fn1.int$3$.float$2$.bool$4$, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		st	%i2, [%fp+76]
		
		! i[0]
		set	0, %o0
		set	3, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	68, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-4, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = i[0]
		set	-8, %o1
		add	%fp, %o1, %o1
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! f[1]
		set	1, %o0
		set	2, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	72, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-12, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = f[1]
		set	-16, %o1
		add	%fp, %o1, %o1
		set	-12, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
				! b[3]
		set	3, %o0
		set	4, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	76, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-20, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = b[3]
		set	-24, %o1
		add	%fp, %o1, %o1
		set	-20, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
			
	! End of function fn1.int$3$.float$2$.bool$4$
	call	fn1.int$3$.float$2$.bool$4$.fini
	nop
	ret
	restore
	SAVE.fn1.int$3$.float$2$.bool$4$ = -(92 + 24) & -8
	
fn1.int$3$.float$2$.bool$4$.fini:
	save	%sp, -96, %sp
	ret
	restore

fn1.MYS_1:
	set	SAVE.fn1.MYS_1, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		
		! b.MYS_1(...)
		set	-4, %o0
		add	%fp, %o0, %o0
		call	MYS_1.MYS_1.void
		nop
		
		.section	".bss"
		.align	4
	.$$.ctorDtor.1:
		.skip	4
		
		.section	".text"
		.align	4
		
		set	.$$.ctorDtor.1, %o0
		set	-4, %o1
		add	%fp, %o1, %o1
		st	%o1, [%o0]
		
		! b = a
		set	-4, %o0
		add	%fp, %o0, %o0
		set	68, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! a = b
		set	68, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-4, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		
		! b = b
		set	-4, %o0
		add	%fp, %o0, %o0
		set	-4, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		
		! a = a
		set	68, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	68, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! a.x
		set	68, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	0, %o1
		add	%g0, %o1, %o1
		add	%o0, %o1, %o0
		set	-8, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = x
		set	-12, %o1
		add	%fp, %o1, %o1
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
			
	! End of function fn1.MYS_1
	call	fn1.MYS_1.fini
	nop
	ret
	restore
	SAVE.fn1.MYS_1 = -(92 + 12) & -8
	
fn1.MYS_1.fini:
	save	%sp, -96, %sp
	set	.$$.ctorDtor.1, %o0
	ld	[%o0], %o0
	cmp	%o0, %g0
	be	.$$.ctorDtor.1.fini.skip
	nop
	call	MYS_1.$MYS_1.void
	nop
	set	.$$.ctorDtor.1, %o0
	st	%g0, [%o0]
.$$.ctorDtor.1.fini.skip:
	ret
	restore

fn1.MYS_1.int:
	set	SAVE.fn1.MYS_1.int, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		
	
	! End of function fn1.MYS_1.int
	call	fn1.MYS_1.int.fini
	nop
	ret
	restore
	SAVE.fn1.MYS_1.int = -(92 + 0) & -8
	
fn1.MYS_1.int.fini:
	save	%sp, -96, %sp
	ret
	restore

	.global	fn2
fn2:
fn2.MYS_1:
	set	SAVE.fn2.MYS_1, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		
		! b.MYS_1(...)
		set	-4, %o0
		add	%fp, %o0, %o0
		call	MYS_1.MYS_1.void
		nop
		
		.section	".bss"
		.align	4
	.$$.ctorDtor.2:
		.skip	4
		
		.section	".text"
		.align	4
		
		set	.$$.ctorDtor.2, %o0
		set	-4, %o1
		add	%fp, %o1, %o1
		st	%o1, [%o0]
		
		! b = a
		set	-4, %o0
		add	%fp, %o0, %o0
		set	68, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! a.x
		set	68, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	0, %o1
		add	%g0, %o1, %o1
		add	%o0, %o1, %o0
		set	-8, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = x
		set	-12, %o1
		add	%fp, %o1, %o1
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
			
	! End of function fn2.MYS_1
	call	fn2.MYS_1.fini
	nop
	ret
	restore
	SAVE.fn2.MYS_1 = -(92 + 12) & -8
	
fn2.MYS_1.fini:
	save	%sp, -96, %sp
	set	.$$.ctorDtor.2, %o0
	ld	[%o0], %o0
	cmp	%o0, %g0
	be	.$$.ctorDtor.2.fini.skip
	nop
	call	MYS_1.$MYS_1.void
	nop
	set	.$$.ctorDtor.2, %o0
	st	%g0, [%o0]
.$$.ctorDtor.2.fini.skip:
	ret
	restore

fn2.MYS_1.int:
	set	SAVE.fn2.MYS_1.int, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		
	
	! End of function fn2.MYS_1.int
	call	fn2.MYS_1.int.fini
	nop
	ret
	restore
	SAVE.fn2.MYS_1.int = -(92 + 0) & -8
	
fn2.MYS_1.int.fini:
	save	%sp, -96, %sp
	ret
	restore

fn2.int$.float$.bool$:
	set	SAVE.fn2.int$.float$.bool$, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		st	%i2, [%fp+76]
		
		! x = a
		set	-4, %o1
		add	%fp, %o1, %o1
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-8, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-16, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = a
		set	-8, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-16, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-24, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-32, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = x
		set	-24, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-32, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! y = b
		set	-40, %o1
		add	%fp, %o1, %o1
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *y
		set	-40, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-44, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-52, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = b
		set	-44, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-52, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-60, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-40, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-68, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = y
		set	-60, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
		! *y
		set	-40, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-76, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-84, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = a
		set	-76, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-84, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		set	-92, %l7
		add	%fp, %l7, %l7
		st	%o0, [%l7]
		ld	[%l7], %f0
		fitos	%f0, %f0
		st	%f0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-96, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-104, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = x
		set	-96, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		set	-112, %l7
		add	%fp, %l7, %l7
		st	%o0, [%l7]
		ld	[%l7], %f0
		fitos	%f0, %f0
		st	%f0, [%o1]
		
		! z = c
		set	-116, %o1
		add	%fp, %o1, %o1
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *z
		set	-116, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-120, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-128, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = c
		set	-120, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-128, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-136, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-116, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-144, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! c = z
		set	-136, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-144, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
	
	! End of function fn2.int$.float$.bool$
	call	fn2.int$.float$.bool$.fini
	nop
	ret
	restore
	SAVE.fn2.int$.float$.bool$ = -(92 + 148) & -8
	
fn2.int$.float$.bool$.fini:
	save	%sp, -96, %sp
	ret
	restore

	.global	fn3
fn3:
fn3.int$.float$.bool$:
	set	SAVE.fn3.int$.float$.bool$, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		st	%i2, [%fp+76]
		
		! x = a
		set	-4, %o1
		add	%fp, %o1, %o1
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-8, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-16, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = a
		set	-8, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-16, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-24, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-32, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = x
		set	-24, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-32, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! y = b
		set	-40, %o1
		add	%fp, %o1, %o1
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *y
		set	-40, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-44, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-52, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = b
		set	-44, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-52, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-60, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-40, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-68, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = y
		set	-60, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
		! *y
		set	-40, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-76, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-84, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = a
		set	-76, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-84, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		set	-92, %l7
		add	%fp, %l7, %l7
		st	%o0, [%l7]
		ld	[%l7], %f0
		fitos	%f0, %f0
		st	%f0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-96, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-104, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = x
		set	-96, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		set	-112, %l7
		add	%fp, %l7, %l7
		st	%o0, [%l7]
		ld	[%l7], %f0
		fitos	%f0, %f0
		st	%f0, [%o1]
		
		! z = c
		set	-116, %o1
		add	%fp, %o1, %o1
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *z
		set	-116, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-120, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-128, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = c
		set	-120, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-128, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-136, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-116, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-144, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! c = z
		set	-136, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-144, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
	
	! End of function fn3.int$.float$.bool$
	call	fn3.int$.float$.bool$.fini
	nop
	ret
	restore
	SAVE.fn3.int$.float$.bool$ = -(92 + 148) & -8
	
fn3.int$.float$.bool$.fini:
	save	%sp, -96, %sp
	ret
	restore

fn2.int$$.float$$.bool$$:
	set	SAVE.fn2.int$$.float$$.bool$$, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		st	%i2, [%fp+76]
		
		! x = a
		set	-4, %o1
		add	%fp, %o1, %o1
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-8, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-16, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = a
		set	-8, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-16, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-24, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-32, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = x
		set	-24, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-32, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-40, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-40, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-48, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-56, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	-56, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-64, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = a
		set	-48, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-64, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-72, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	-72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-80, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-88, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-88, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-96, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = x
		set	-80, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-96, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! y = b
		set	-104, %o1
		add	%fp, %o1, %o1
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *y
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-108, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-116, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = b
		set	-108, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-116, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-124, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-132, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = y
		set	-124, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-132, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *y
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-140, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-140, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-148, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-156, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-156, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-164, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = b
		set	-148, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-164, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-172, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-172, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-180, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-188, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-188, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-196, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = y
		set	-180, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-196, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
		! *y
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-204, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-204, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-212, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-220, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	-220, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-228, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = a
		set	-212, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-228, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		set	-236, %l7
		add	%fp, %l7, %l7
		st	%o0, [%l7]
		ld	[%l7], %f0
		fitos	%f0, %f0
		st	%f0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-240, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-240, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-248, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-256, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-256, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-264, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = x
		set	-248, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-264, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		set	-272, %l7
		add	%fp, %l7, %l7
		st	%o0, [%l7]
		ld	[%l7], %f0
		fitos	%f0, %f0
		st	%f0, [%o1]
		
		! z = c
		set	-276, %o1
		add	%fp, %o1, %o1
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *z
		set	-276, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-280, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-288, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = c
		set	-280, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-288, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-296, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-276, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-304, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! c = z
		set	-296, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-304, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *z
		set	-276, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-312, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-312, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-320, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-328, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *c
		set	-328, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-336, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = c
		set	-320, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-336, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-344, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *c
		set	-344, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-352, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-276, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-360, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-360, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-368, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! c = z
		set	-352, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-368, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
	
	! End of function fn2.int$$.float$$.bool$$
	call	fn2.int$$.float$$.bool$$.fini
	nop
	ret
	restore
	SAVE.fn2.int$$.float$$.bool$$ = -(92 + 372) & -8
	
fn2.int$$.float$$.bool$$.fini:
	save	%sp, -96, %sp
	ret
	restore

fn3.int$$.float$$.bool$$:
	set	SAVE.fn3.int$$.float$$.bool$$, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		st	%i2, [%fp+76]
		
		! x = a
		set	-4, %o1
		add	%fp, %o1, %o1
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-8, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-16, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = a
		set	-8, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-16, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-24, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-32, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = x
		set	-24, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-32, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-40, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-40, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-48, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-56, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	-56, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-64, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = a
		set	-48, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-64, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-72, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	-72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-80, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-88, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-88, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-96, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = x
		set	-80, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-96, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! y = b
		set	-104, %o1
		add	%fp, %o1, %o1
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *y
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-108, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-116, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = b
		set	-108, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-116, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-124, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-132, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = y
		set	-124, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-132, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *y
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-140, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-140, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-148, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-156, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-156, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-164, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = b
		set	-148, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-164, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-172, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-172, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-180, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-188, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-188, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-196, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = y
		set	-180, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-196, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
		! *y
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-204, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-204, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-212, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-220, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	-220, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-228, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = a
		set	-212, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-228, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		set	-236, %l7
		add	%fp, %l7, %l7
		st	%o0, [%l7]
		ld	[%l7], %f0
		fitos	%f0, %f0
		st	%f0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-240, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-240, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-248, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-256, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-256, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-264, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = x
		set	-248, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-264, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		set	-272, %l7
		add	%fp, %l7, %l7
		st	%o0, [%l7]
		ld	[%l7], %f0
		fitos	%f0, %f0
		st	%f0, [%o1]
		
		! z = c
		set	-276, %o1
		add	%fp, %o1, %o1
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! *z
		set	-276, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-280, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-288, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = c
		set	-280, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-288, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-296, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-276, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-304, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! c = z
		set	-296, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-304, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *z
		set	-276, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-312, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-312, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-320, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-328, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *c
		set	-328, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-336, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = c
		set	-320, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-336, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *c
		set	76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-344, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *c
		set	-344, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-352, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-276, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-360, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-360, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-368, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! c = z
		set	-352, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-368, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
	
	! End of function fn3.int$$.float$$.bool$$
	call	fn3.int$$.float$$.bool$$.fini
	nop
	ret
	restore
	SAVE.fn3.int$$.float$$.bool$$ = -(92 + 372) & -8
	
fn3.int$$.float$$.bool$$.fini:
	save	%sp, -96, %sp
	ret
	restore

	.global	fn4
fn4:
fn4.int$$3$.float$$2$.bool$$4$:
	set	SAVE.fn4.int$$3$.float$$2$.bool$$4$, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		st	%i2, [%fp+76]
		
				! i[0]
		set	0, %o0
		set	3, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	68, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-8, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = i[0]
		set	-4, %o1
		add	%fp, %o1, %o1
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-12, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! i[0]
		set	0, %o0
		set	3, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	68, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-20, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *i[0]
		set	-20, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-24, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = i[0]
		set	-12, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-24, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! f[1]
		set	1, %o0
		set	2, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	72, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-36, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = f[1]
		set	-32, %o1
		add	%fp, %o1, %o1
		set	-36, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *y
		set	-32, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-40, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! f[1]
		set	1, %o0
		set	2, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	72, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-48, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *f[1]
		set	-48, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-52, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = f[1]
		set	-40, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-52, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
				! b[3]
		set	3, %o0
		set	4, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	76, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-64, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = b[3]
		set	-60, %o1
		add	%fp, %o1, %o1
		set	-64, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *z
		set	-60, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-68, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b[3]
		set	3, %o0
		set	4, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	76, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-76, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b[3]
		set	-76, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-80, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = b[3]
		set	-68, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-80, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
	
	! End of function fn4.int$$3$.float$$2$.bool$$4$
	call	fn4.int$$3$.float$$2$.bool$$4$.fini
	nop
	ret
	restore
	SAVE.fn4.int$$3$.float$$2$.bool$$4$ = -(92 + 84) & -8
	
fn4.int$$3$.float$$2$.bool$$4$.fini:
	save	%sp, -96, %sp
	ret
	restore

	.global	fn5
fn5:
fn5.int$$$3$.float$$$2$.bool$$$4$:
	set	SAVE.fn5.int$$$3$.float$$$2$.bool$$$4$, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		st	%i2, [%fp+76]
		
				! i[0]
		set	0, %o0
		set	3, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	68, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-8, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = i[0]
		set	-4, %o1
		add	%fp, %o1, %o1
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-12, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! i[0]
		set	0, %o0
		set	3, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	68, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-20, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *i[0]
		set	-20, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-24, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = i[0]
		set	-12, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-24, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-32, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-32, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-40, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! i[0]
		set	0, %o0
		set	3, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	68, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-48, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *i[0]
		set	-48, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-52, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *i[0]
		set	-52, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-60, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = i[0]
		set	-40, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-60, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
				! f[1]
		set	1, %o0
		set	2, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	72, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-72, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = f[1]
		set	-68, %o1
		add	%fp, %o1, %o1
		set	-72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *y
		set	-68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-76, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! f[1]
		set	1, %o0
		set	2, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	72, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-84, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *f[1]
		set	-84, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-88, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = f[1]
		set	-76, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-88, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *y
		set	-68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-96, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-96, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-104, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! f[1]
		set	1, %o0
		set	2, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	72, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-112, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *f[1]
		set	-112, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-116, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *f[1]
		set	-116, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-124, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = f[1]
		set	-104, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-124, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %f0
		st	%f0, [%o1]
		
				! b[3]
		set	3, %o0
		set	4, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	76, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-136, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = b[3]
		set	-132, %o1
		add	%fp, %o1, %o1
		set	-136, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *z
		set	-132, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-140, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b[3]
		set	3, %o0
		set	4, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	76, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-148, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b[3]
		set	-148, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-152, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = b[3]
		set	-140, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-152, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *z
		set	-132, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-160, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *z
		set	-160, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-168, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b[3]
		set	3, %o0
		set	4, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	76, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-176, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b[3]
		set	-176, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-180, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b[3]
		set	-180, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-188, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = b[3]
		set	-168, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-188, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
	
	! End of function fn5.int$$$3$.float$$$2$.bool$$$4$
	call	fn5.int$$$3$.float$$$2$.bool$$$4$.fini
	nop
	ret
	restore
	SAVE.fn5.int$$$3$.float$$$2$.bool$$$4$ = -(92 + 192) & -8
	
fn5.int$$$3$.float$$$2$.bool$$$4$.fini:
	save	%sp, -96, %sp
	ret
	restore

fn5.MYS_1$.MYS_1$:
	set	SAVE.fn5.MYS_1$.MYS_1$, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		
				! y.MYS_1(...)
		set	-8, %o0
		add	%fp, %o0, %o0
		call	MYS_1.MYS_1.void
		nop
		
		.section	".bss"
		.align	4
	.$$.ctorDtor.3:
		.skip	4
		
		.section	".text"
		.align	4
		
		set	.$$.ctorDtor.3, %o0
		set	-8, %o1
		add	%fp, %o1, %o1
		st	%o1, [%o0]
		
		! x = a
		set	-4, %o1
		add	%fp, %o1, %o1
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! a = x
		set	68, %o1
		add	%fp, %o1, %o1
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-12, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-20, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = x
		set	-12, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-20, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-28, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-36, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = a
		set	-28, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-36, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-44, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = a
		set	-8, %o0
		add	%fp, %o0, %o0
		set	-44, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-52, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = y
		set	-52, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-8, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-60, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = y
		set	-60, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-8, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-68, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = x
		set	-8, %o0
		add	%fp, %o0, %o0
		set	-68, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! x = b
		set	-4, %o1
		add	%fp, %o1, %o1
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! b = x
		set	72, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-76, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-84, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = b
		set	-76, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-84, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-92, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-100, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = x
		set	-92, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-100, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-108, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = b
		set	-8, %o0
		add	%fp, %o0, %o0
		set	-108, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-116, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = y
		set	-116, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-8, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		
	
	! End of function fn5.MYS_1$.MYS_1$
	call	fn5.MYS_1$.MYS_1$.fini
	nop
	ret
	restore
	SAVE.fn5.MYS_1$.MYS_1$ = -(92 + 120) & -8
	
fn5.MYS_1$.MYS_1$.fini:
	save	%sp, -96, %sp
	set	.$$.ctorDtor.3, %o0
	ld	[%o0], %o0
	cmp	%o0, %g0
	be	.$$.ctorDtor.3.fini.skip
	nop
	call	MYS_1.$MYS_1.void
	nop
	set	.$$.ctorDtor.3, %o0
	st	%g0, [%o0]
.$$.ctorDtor.3.fini.skip:
	ret
	restore

fn5.MYS_1$$.MYS_1$$:
	set	SAVE.fn5.MYS_1$$.MYS_1$$, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		
						! z.MYS_1(...)
		set	-12, %o0
		add	%fp, %o0, %o0
		call	MYS_1.MYS_1.void
		nop
		
		.section	".bss"
		.align	4
	.$$.ctorDtor.4:
		.skip	4
		
		.section	".text"
		.align	4
		
		set	.$$.ctorDtor.4, %o0
		set	-12, %o1
		add	%fp, %o1, %o1
		st	%o1, [%o0]
		
		! x = a
		set	-4, %o1
		add	%fp, %o1, %o1
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! a = x
		set	68, %o1
		add	%fp, %o1, %o1
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-16, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-24, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = x
		set	-16, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-24, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-32, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-40, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = a
		set	-32, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-40, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-48, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	-48, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-56, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-64, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-64, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-72, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = x
		set	-56, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-72, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-80, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-80, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-88, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-96, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	-96, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-104, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = a
		set	-88, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-104, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-112, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = a
		set	-8, %o1
		add	%fp, %o1, %o1
		set	-112, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-120, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = y
		set	-120, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-128, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = y
		set	-128, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-136, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = x
		set	-8, %o1
		add	%fp, %o1, %o1
		set	-136, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-144, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-144, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-152, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-160, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = y
		set	-152, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-160, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *y
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-168, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-176, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-176, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-184, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = x
		set	-168, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-184, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! x = b
		set	-4, %o1
		add	%fp, %o1, %o1
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! b = x
		set	72, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-192, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-200, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = b
		set	-192, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-200, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-208, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-216, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = x
		set	-208, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-216, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-224, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-224, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-232, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-240, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-240, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-248, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = b
		set	-232, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-248, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-256, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-256, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-264, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-272, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-272, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-280, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = x
		set	-264, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-280, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-288, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = b
		set	-8, %o1
		add	%fp, %o1, %o1
		set	-288, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-296, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = y
		set	-296, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		st	%o0, [%o1]
		
		! *y
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-304, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-312, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-312, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-320, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = b
		set	-304, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-320, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-328, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-328, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-336, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *y
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-344, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = y
		set	-336, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-344, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-352, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	-352, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-360, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = a
		set	-12, %o0
		add	%fp, %o0, %o0
		set	-360, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *a
		set	68, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-368, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *a
		set	-368, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-376, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! a = z
		set	-376, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-12, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-384, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-384, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-392, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = b
		set	-12, %o0
		add	%fp, %o0, %o0
		set	-392, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *b
		set	72, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-400, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *b
		set	-400, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-408, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b = z
		set	-408, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-12, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-416, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-416, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-424, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = x
		set	-12, %o0
		add	%fp, %o0, %o0
		set	-424, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
		! *x
		set	-4, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-432, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! *x
		set	-432, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-440, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! x = z
		set	-440, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-12, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		
		! *y
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-448, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! y = z
		set	-448, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-12, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		
		! *y
		set	-8, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-456, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! z = y
		set	-12, %o0
		add	%fp, %o0, %o0
		set	-456, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		
	
	! End of function fn5.MYS_1$$.MYS_1$$
	call	fn5.MYS_1$$.MYS_1$$.fini
	nop
	ret
	restore
	SAVE.fn5.MYS_1$$.MYS_1$$ = -(92 + 460) & -8
	
fn5.MYS_1$$.MYS_1$$.fini:
	save	%sp, -96, %sp
	set	.$$.ctorDtor.4, %o0
	ld	[%o0], %o0
	cmp	%o0, %g0
	be	.$$.ctorDtor.4.fini.skip
	nop
	call	MYS_1.$MYS_1.void
	nop
	set	.$$.ctorDtor.4, %o0
	st	%g0, [%o0]
.$$.ctorDtor.4.fini.skip:
	ret
	restore

MYS_2.MYS_2.void:
	set	SAVE.MYS_2.MYS_2.void, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
	
	! End of function MYS_2.MYS_2.void
	call	MYS_2.MYS_2.void.fini
	nop
	ret
	restore
	SAVE.MYS_2.MYS_2.void = -(92 + 0) & -8
	
MYS_2.MYS_2.void.fini:
	save	%sp, -96, %sp
	ret
	restore

MYS_2.$MYS_2.void:
	set	SAVE.MYS_2.$MYS_2.void, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
	
	! End of function MYS_2.$MYS_2.void
	call	MYS_2.$MYS_2.void.fini
	nop
	ret
	restore
	SAVE.MYS_2.$MYS_2.void = -(92 + 0) & -8
	
MYS_2.$MYS_2.void.fini:
	save	%sp, -96, %sp
	ret
	restore

	.global	fn10
fn10:
fn10.MYS_1.MYS_1.MYS_2:
	set	SAVE.fn10.MYS_1.MYS_1.MYS_2, %g1
	save	%sp, %g1, %sp
	
		! Store params
		st	%i0, [%fp+68]
		st	%i1, [%fp+72]
		st	%i2, [%fp+76]
		
	
	! End of function fn10.MYS_1.MYS_1.MYS_2
	call	fn10.MYS_1.MYS_1.MYS_2.fini
	nop
	ret
	restore
	SAVE.fn10.MYS_1.MYS_1.MYS_2 = -(92 + 0) & -8
	
fn10.MYS_1.MYS_1.MYS_2.fini:
	save	%sp, -96, %sp
	ret
	restore

	.global	main
main:
main.void:
	set	SAVE.main.void, %g1
	save	%sp, %g1, %sp
	
		! Store params
		
														! a.MYS_1(...)
		set	-76, %o0
		add	%fp, %o0, %o0
		call	MYS_1.MYS_1.void
		nop
		
		.section	".bss"
		.align	4
	.$$.ctorDtor.5:
		.skip	4
		
		.section	".text"
		.align	4
		
		set	.$$.ctorDtor.5, %o0
		set	-76, %o1
		add	%fp, %o1, %o1
		st	%o1, [%o0]
		
				! fn1(...)
		! i <- i
		set	-12, %o0
		add	%fp, %o0, %o0
		! f <- f
		set	-20, %o1
		add	%fp, %o1, %o1
		! b <- b
		set	-36, %o2
		add	%fp, %o2, %o2
		call	fn1.int$3$.float$2$.bool$4$
		nop
		
		! fn1(...)
		! a <- a
		set	-76, %o0
		add	%fp, %o0, %o0
		call	fn1.MYS_1
		nop
		
		! *aa
		set	-80, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-84, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! fn1(...)
		! a <- aa
		set	-84, %o0
		add	%fp, %o0, %o0
		call	fn1.MYS_1
		nop
		
		! fn2(...)
		! a <- a
		mov	%o0, %l2
		mov	%o1, %l3
		mov	%o2, %l4
		mov	%o3, %l5
		mov	%o4, %l6
		mov	%o5, %l7
		set	-16, %l1
		add	%fp, %l1, %o0
		set	-76, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		mov	%l2, %o0
		mov	%l3, %o1
		mov	%l4, %o2
		mov	%l5, %o3
		mov	%l6, %o4
		mov	%l7, %o5
		set	-16, %l1
		add	%fp, %l1, %o0
		call	fn2.MYS_1
		nop
		
		! *aa
		set	-80, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		call	.$$.ptrCheck
		nop
		set	-92, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! fn2(...)
		! a <- aa
		mov	%o0, %l2
		mov	%o1, %l3
		mov	%o2, %l4
		mov	%o3, %l5
		mov	%o4, %l6
		mov	%o5, %l7
		set	-20, %l1
		add	%fp, %l1, %o0
		set	-92, %o1
		add	%fp, %o1, %o1
		ld	[%o1], %o1
		set	4, %o2
		call	memmove
		nop
		mov	%l2, %o0
		mov	%l3, %o1
		mov	%l4, %o2
		mov	%l5, %o3
		mov	%l6, %o4
		mov	%l7, %o5
		set	-20, %l1
		add	%fp, %l1, %o0
		call	fn2.MYS_1
		nop
		
		! ii[0]
		set	0, %o0
		set	3, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-48, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-100, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! ff[1]
		set	1, %o0
		set	2, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-56, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-104, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! bb[3]
		set	3, %o0
		set	4, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-72, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-108, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! fn2(...)
		! a <- ii[0]
		set	-100, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o0
		! b <- ff[1]
		set	-104, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o1
		! c <- bb[3]
		set	-108, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %l7
		ld	[%l7], %o2
		call	fn2.int$.float$.bool$
		nop
		
		! i[0]
		set	0, %o0
		set	3, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-12, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-112, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! &i[0]
		set	-112, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-116, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! f[1]
		set	1, %o0
		set	2, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-20, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-120, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! &f[1]
		set	-120, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-124, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! b[3]
		set	3, %o0
		set	4, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-36, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-128, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! &b[3]
		set	-128, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-132, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! fn2(...)
		! a <- &i[0]
		set	-116, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		! b <- &f[1]
		set	-124, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o1
		! c <- &b[3]
		set	-132, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o2
		call	fn2.int$.float$.bool$
		nop
		
		! ii[0]
		set	0, %o0
		set	3, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-48, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-136, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! ff[1]
		set	1, %o0
		set	2, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-56, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-140, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! bb[3]
		set	3, %o0
		set	4, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-72, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-144, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! fn3(...)
		! a <- ii[0]
		set	-136, %o0
		add	%fp, %o0, %o0
		! b <- ff[1]
		set	-140, %o1
		add	%fp, %o1, %o1
		! c <- bb[3]
		set	-144, %o2
		add	%fp, %o2, %o2
		call	fn3.int$.float$.bool$
		nop
		
		! ii[0]
		set	0, %o0
		set	3, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-48, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-148, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! &ii[0]
		set	-148, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-152, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! ff[1]
		set	1, %o0
		set	2, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-56, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-156, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! &ff[1]
		set	-156, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-160, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! bb[3]
		set	3, %o0
		set	4, %o1
		call	.$$.arrCheck
		nop
		set	4, %o1
		call	.mul
		nop
		mov	%o0, %o1
		set	-72, %o0
		add	%fp, %o0, %o0
		call	.$$.ptrCheck
		nop
		add	%o0, %o1, %o0
		set	-164, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! &bb[3]
		set	-164, %o0
		add	%fp, %o0, %o0
		ld	[%o0], %o0
		set	-168, %o1
		add	%fp, %o1, %o1
		st	%o0, [%o1]
		
		! fn2(...)
		! a <- &ii[0]
		set	-152, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		! b <- &ff[1]
		set	-160, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o1
		! c <- &bb[3]
		set	-168, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o2
		call	fn2.int$$.float$$.bool$$
		nop
		
		! fn4(...)
		! i <- ii
		set	-48, %o0
		add	%fp, %o0, %o0
		! f <- ff
		set	-56, %o1
		add	%fp, %o1, %o1
		! b <- bb
		set	-72, %o2
		add	%fp, %o2, %o2
		call	fn4.int$$3$.float$$2$.bool$$4$
		nop
		
		! fn5(...)
		! a <- aa
		set	-80, %l7
		add	%fp, %l7, %l7
		ld	[%l7], %o0
		! b <- aa
		set	-80, %o1
		add	%fp, %o1, %o1
		call	fn5.MYS_1$.MYS_1$
		nop
		
		! c.MYS_2(...)
		set	-184, %o0
		add	%fp, %o0, %o0
		call	MYS_2.MYS_2.void
		nop
		
		.section	".bss"
		.align	4
	.$$.ctorDtor.6:
		.skip	4
		
		.section	".text"
		.align	4
		
		set	.$$.ctorDtor.6, %o0
		set	-184, %o1
		add	%fp, %o1, %o1
		st	%o1, [%o0]
		
		! fn10(...)
		! a <- a
		mov	%o0, %l2
		mov	%o1, %l3
		mov	%o2, %l4
		mov	%o3, %l5
		mov	%o4, %l6
		mov	%o5, %l7
		set	-4, %l1
		add	%fp, %l1, %o0
		set	-76, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		mov	%l2, %o0
		mov	%l3, %o1
		mov	%l4, %o2
		mov	%l5, %o3
		mov	%l6, %o4
		mov	%l7, %o5
		set	-4, %l1
		add	%fp, %l1, %o0
		! b <- a
		mov	%o0, %l2
		mov	%o1, %l3
		mov	%o2, %l4
		mov	%o3, %l5
		mov	%o4, %l6
		mov	%o5, %l7
		set	-8, %l1
		add	%fp, %l1, %o0
		set	-76, %o1
		add	%fp, %o1, %o1
		set	4, %o2
		call	memmove
		nop
		mov	%l2, %o0
		mov	%l3, %o1
		mov	%l4, %o2
		mov	%l5, %o3
		mov	%l6, %o4
		mov	%l7, %o5
		set	-8, %l1
		add	%fp, %l1, %o1
		! c <- c
		mov	%o0, %l2
		mov	%o1, %l3
		mov	%o2, %l4
		mov	%o3, %l5
		mov	%o4, %l6
		mov	%o5, %l7
		set	-24, %l1
		add	%fp, %l1, %o0
		set	-184, %o1
		add	%fp, %o1, %o1
		set	16, %o2
		call	memmove
		nop
		mov	%l2, %o0
		mov	%l3, %o1
		mov	%l4, %o2
		mov	%l5, %o3
		mov	%l6, %o4
		mov	%l7, %o5
		set	-24, %l1
		add	%fp, %l1, %o2
		call	fn10.MYS_1.MYS_1.MYS_2
		nop
		
	
	! End of function main.void
	call	main.void.fini
	nop
	ret
	restore
	SAVE.main.void = -(92 + 184) & -8
	
main.void.fini:
	save	%sp, -96, %sp
	set	.$$.ctorDtor.6, %o0
	ld	[%o0], %o0
	cmp	%o0, %g0
	be	.$$.ctorDtor.6.fini.skip
	nop
	call	MYS_2.$MYS_2.void
	nop
	set	.$$.ctorDtor.6, %o0
	st	%g0, [%o0]
.$$.ctorDtor.6.fini.skip:
	set	.$$.ctorDtor.5, %o0
	ld	[%o0], %o0
	cmp	%o0, %g0
	be	.$$.ctorDtor.5.fini.skip
	nop
	call	MYS_1.$MYS_1.void
	nop
	set	.$$.ctorDtor.5, %o0
	st	%g0, [%o0]
.$$.ctorDtor.5.fini.skip:
	ret
	restore

