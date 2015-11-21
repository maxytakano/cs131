
/*
 * Generated Thu May 28 01:32:28 PDT 2015
 */


	.section	".rodata"
	.align  	4
.$$.intFmt:
	.asciz  	"%d"
.$$.strFmt:
	.asciz  	"%s"
.$$.strTF:
	.asciz  	"false\0\0\0true"
.$$.strEndl:
	.asciz  	"\n"
.$$.strArrBound:
	.asciz  	"Index value of %d is outside legal range [0,%d).\n"
.$$.strNullPtr:
	.asciz  	"Attempt to dereference NULL pointer.\n"

	.section	".text"
	.align  	4
.$$.printBool:
	save    	%sp, -96, %sp
	set     	.$$.strTF, %o0
	cmp     	%g0, %i0
	be      	.$$.printBool2
	nop
	add     	%o0, 8, %o0
.$$.printBool2:
	call    	printf
	nop
	ret
	restore

.$$.arrCheck:
	save    	%sp, -96, %sp
	cmp     	%i0, %g0
	bl      	.$$.arrCheck2
	nop
	cmp     	%i0, %i1
	bge     	.$$.arrCheck2
	nop
	ret
	restore
.$$.arrCheck2:
	set     	.$$.strArrBound, %o0
	mov     	%i0, %o1
	call    	printf
	mov     	%i1, %o2
	call    	exit
	mov     	1, %o0
	ret
	restore

.$$.ptrCheck:
	save    	%sp, -96, %sp
	cmp     	%i0, %g0
	bne     	.$$.ptrCheck2
	nop
	set     	.$$.strNullPtr, %o0
	call    	printf
	nop
	call    	exit
	mov     	1, %o0
.$$.ptrCheck2:
	ret
	restore

MYS_A.MYS_A.void:
	set     	SAVE.MYS_A.MYS_A.void, %g1
	save    	%sp, %g1, %sp

		! Store params
		st      	%i0, [%fp+68]

		.section	".rodata"
		.align  	4
	.$$.str.1:
		.asciz  	"ctor called MYS_A"

		.section	".text"
		.align  	4
		! cout << "ctor called MYS_A"
		set     	.$$.strFmt, %o0
		set     	.$$.str.1, %o1
		call    	printf
		nop

		! cout << endl
		set     	.$$.strEndl, %o0
		call    	printf
		nop

	! End of function MYS_A.MYS_A.void
	call    	MYS_A.MYS_A.void.fini
	nop
	ret
	restore
	SAVE.MYS_A.MYS_A.void = -(92 + 0) & -8

MYS_A.MYS_A.void.fini:
	save    	%sp, -96, %sp
	ret
	restore
MYS_A.$MYS_A.void:
	set     	SAVE.MYS_A.$MYS_A.void, %g1
	save    	%sp, %g1, %sp

		! Store params
		st      	%i0, [%fp+68]

	! End of function MYS_A.$MYS_A.void
	call    	MYS_A.$MYS_A.void.fini
	nop
	ret
	restore
	SAVE.MYS_A.$MYS_A.void = -(92 + 0) & -8

MYS_A.$MYS_A.void.fini:
	save    	%sp, -96, %sp
	ret
	restore
MYS_B.MYS_B.int:
	set     	SAVE.MYS_B.MYS_B.int, %g1
	save    	%sp, %g1, %sp

		! Store params
		st      	%i0, [%fp+68]
		st      	%i1, [%fp+72]

		.section	".rodata"
		.align  	4
	.$$.str.2:
		.asciz  	"ctor called MYS_B"

		.section	".text"
		.align  	4
		! cout << "ctor called MYS_B"
		set     	.$$.strFmt, %o0
		set     	.$$.str.2, %o1
		call    	printf
		nop

		! cout << endl
		set     	.$$.strEndl, %o0
		call    	printf
		nop

		! a.MYS_A(...)
		set     	-4, %o0
		add     	%fp, %o0, %o0
		call    	MYS_A.MYS_A.void
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.1:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.1, %o0
		set     	-4, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

		! this.f
		set     	68, %o0
		add     	%fp, %o0, %o0
		ld      	[%o0], %o0
		set     	4, %o1
		add     	%g0, %o1, %o1
		add     	%o0, %o1, %o0
		set     	-8, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! new( this.f )
		mov     	1, %o0
		set     	4, %o1
		call    	calloc
		nop
		set     	-8, %o1
		add     	%fp, %o1, %o1
		ld      	[%o1], %o1
		st      	%o0, [%o1]

		! this.m
		set     	68, %o0
		add     	%fp, %o0, %o0
		ld      	[%o0], %o0
		set     	8, %o1
		add     	%g0, %o1, %o1
		add     	%o0, %o1, %o0
		set     	-12, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! new( this.m )
		mov     	1, %o0
		set     	0, %o1
		call    	calloc
		nop
		set     	-12, %o1
		add     	%fp, %o1, %o1
		ld      	[%o1], %o1
		st      	%o0, [%o1]

		! *this.m
		set     	-12, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		call    	.$$.ptrCheck
		nop
		set     	-16, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! *this.m.MYS_B(...)
		set     	-16, %o0
		add     	%fp, %o0, %o0
		ld      	[%o0], %o0
		! x <- 1
		set     	1, %o1
		call    	MYS_B.MYS_B.int
		nop

	! End of function MYS_B.MYS_B.int
	call    	MYS_B.MYS_B.int.fini
	nop
	ret
	restore
	SAVE.MYS_B.MYS_B.int = -(92 + 16) & -8

MYS_B.MYS_B.int.fini:
	save    	%sp, -96, %sp
	set     	.$$.ctorDtor.1, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.1.fini.skip
	nop
	call    	MYS_A.$MYS_A.void
	nop
	set     	.$$.ctorDtor.1, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.1.fini.skip:
	ret
	restore
MYS_B.$MYS_B.void:
	set     	SAVE.MYS_B.$MYS_B.void, %g1
	save    	%sp, %g1, %sp

		! Store params
		st      	%i0, [%fp+68]

		! this.f
		set     	68, %o0
		add     	%fp, %o0, %o0
		ld      	[%o0], %o0
		set     	4, %o1
		add     	%g0, %o1, %o1
		add     	%o0, %o1, %o0
		set     	-4, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! delete( this.f )
		set     	-4, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		call    	.$$.ptrCheck
		nop
		set     	-4, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		call    	free
		nop
		set     	-4, %o1
		add     	%fp, %o1, %o1
		ld      	[%o1], %o1
		st      	%g0, [%o1]

		! this.m
		set     	68, %o0
		add     	%fp, %o0, %o0
		ld      	[%o0], %o0
		set     	8, %o1
		add     	%g0, %o1, %o1
		add     	%o0, %o1, %o0
		set     	-8, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! *this.m
		set     	-8, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		call    	.$$.ptrCheck
		nop
		set     	-12, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! *this.m.~MYS_B(...)
		set     	-12, %o0
		add     	%fp, %o0, %o0
		ld      	[%o0], %o0
		call    	MYS_B.$MYS_B.void
		nop

		! delete( this.m )
		set     	-8, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		call    	.$$.ptrCheck
		nop
		set     	-8, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		call    	free
		nop
		set     	-8, %o1
		add     	%fp, %o1, %o1
		ld      	[%o1], %o1
		st      	%g0, [%o1]

		.section	".rodata"
		.align  	4
	.$$.str.3:
		.asciz  	"dtor called MYS_B"

		.section	".text"
		.align  	4
		! cout << "dtor called MYS_B"
		set     	.$$.strFmt, %o0
		set     	.$$.str.3, %o1
		call    	printf
		nop

		! cout << endl
		set     	.$$.strEndl, %o0
		call    	printf
		nop

	! End of function MYS_B.$MYS_B.void
	call    	MYS_B.$MYS_B.void.fini
	nop
	ret
	restore
	SAVE.MYS_B.$MYS_B.void = -(92 + 12) & -8

MYS_B.$MYS_B.void.fini:
	save    	%sp, -96, %sp
	ret
	restore
MYS_B.fn.void:
	set     	SAVE.MYS_B.fn.void, %g1
	save    	%sp, %g1, %sp

		! Store params
		st      	%i0, [%fp+68]

		! a.MYS_A(...)
		set     	-4, %o0
		add     	%fp, %o0, %o0
		call    	MYS_A.MYS_A.void
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.2:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.2, %o0
		set     	-4, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

		! b.MYS_A(...)
		set     	-8, %o0
		add     	%fp, %o0, %o0
		call    	MYS_A.MYS_A.void
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.3:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.3, %o0
		set     	-8, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

	! End of function MYS_B.fn.void
	call    	MYS_B.fn.void.fini
	nop
	ret
	restore
	SAVE.MYS_B.fn.void = -(92 + 8) & -8

MYS_B.fn.void.fini:
	save    	%sp, -96, %sp
	set     	.$$.ctorDtor.3, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.3.fini.skip
	nop
	call    	MYS_A.$MYS_A.void
	nop
	set     	.$$.ctorDtor.3, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.3.fini.skip:
	set     	.$$.ctorDtor.2, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.2.fini.skip
	nop
	call    	MYS_A.$MYS_A.void
	nop
	set     	.$$.ctorDtor.2, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.2.fini.skip:
	ret
	restore

	.section	".bss"
	.align  	4
	.global 	a1
a1:
	.skip   	4

	.section	".text"
	.align  	4
.$.init.a1:
	set     	SAVE..$.init.a1, %g1
	save    	%sp, %g1, %sp

		! a1.MYS_A(...)
		set     	a1, %o0
		add     	%g0, %o0, %o0
		call    	MYS_A.MYS_A.void
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.4:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.4, %o0
		set     	a1, %o1
		add     	%g0, %o1, %o1
		st      	%o1, [%o0]

	! End of function .$.init.a1
	call    	.$.init.a1.fini
	nop
	ret
	restore
	SAVE..$.init.a1 = -(92 + 0) & -8

.$.init.a1.fini:
	save    	%sp, -96, %sp
	ret
	restore

	.section	".init"
	.align  	4
	call    	.$.init.a1
	nop

	.section	".text"
	.align  	4

	.section	".bss"
	.align  	4
b1:
	.skip   	12

	.section	".text"
	.align  	4
.$.init.b1:
	set     	SAVE..$.init.b1, %g1
	save    	%sp, %g1, %sp

		! b1.MYS_B(...)
		set     	b1, %o0
		add     	%g0, %o0, %o0
		! x <- 1
		set     	1, %o1
		call    	MYS_B.MYS_B.int
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.5:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.5, %o0
		set     	b1, %o1
		add     	%g0, %o1, %o1
		st      	%o1, [%o0]

	! End of function .$.init.b1
	call    	.$.init.b1.fini
	nop
	ret
	restore
	SAVE..$.init.b1 = -(92 + 0) & -8

.$.init.b1.fini:
	save    	%sp, -96, %sp
	ret
	restore

	.section	".init"
	.align  	4
	call    	.$.init.b1
	nop

	.section	".text"
	.align  	4

	.section	".bss"
	.align  	4
	.global 	x
x:
	.skip   	4

	.section	".text"
	.align  	4

	.section	".bss"
	.align  	4
	.global 	b2
b2:
	.skip   	12

	.section	".text"
	.align  	4
.$.init.b2:
	set     	SAVE..$.init.b2, %g1
	save    	%sp, %g1, %sp

		! b2.MYS_B(...)
		set     	b2, %o0
		add     	%g0, %o0, %o0
		! x <- x
		set     	x, %l7
		add     	%g0, %l7, %l7
		ld      	[%l7], %o1
		call    	MYS_B.MYS_B.int
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.6:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.6, %o0
		set     	b2, %o1
		add     	%g0, %o1, %o1
		st      	%o1, [%o0]

	! End of function .$.init.b2
	call    	.$.init.b2.fini
	nop
	ret
	restore
	SAVE..$.init.b2 = -(92 + 0) & -8

.$.init.b2.fini:
	save    	%sp, -96, %sp
	ret
	restore

	.section	".init"
	.align  	4
	call    	.$.init.b2
	nop

	.section	".text"
	.align  	4

	.section	".bss"
	.align  	4
a2:
	.skip   	4

	.section	".text"
	.align  	4
.$.init.a2:
	set     	SAVE..$.init.a2, %g1
	save    	%sp, %g1, %sp

		! a2.MYS_A(...)
		set     	a2, %o0
		add     	%g0, %o0, %o0
		call    	MYS_A.MYS_A.void
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.7:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.7, %o0
		set     	a2, %o1
		add     	%g0, %o1, %o1
		st      	%o1, [%o0]

	! End of function .$.init.a2
	call    	.$.init.a2.fini
	nop
	ret
	restore
	SAVE..$.init.a2 = -(92 + 0) & -8

.$.init.a2.fini:
	save    	%sp, -96, %sp
	ret
	restore

	.section	".init"
	.align  	4
	call    	.$.init.a2
	nop

	.section	".text"
	.align  	4
	.global 	test1
test1:
test1.void:
	set     	SAVE.test1.void, %g1
	save    	%sp, %g1, %sp

		! Store params

		! a3.MYS_A(...)
		set     	-4, %o0
		add     	%fp, %o0, %o0
		call    	MYS_A.MYS_A.void
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.8:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.8, %o0
		set     	-4, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

		! a3.x
		set     	-4, %o0
		add     	%fp, %o0, %o0
		set     	0, %o1
		add     	%g0, %o1, %o1
		add     	%o0, %o1, %o0
		set     	-8, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! (a3.x)==(1)
		set     	-8, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		set     	1, %o1
		cmp     	%o0, %o1
		bne     	.$$.cmp.1
		mov     	%g0, %o0
		inc     	%o0
	.$$.cmp.1:
		set     	-12, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! if ( (a3.x)==(1) )
		set     	-12, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %o0
		cmp     	%o0, %g0
		be      	.$$.else.1
		nop

			! a3.x
			set     	-4, %o0
			add     	%fp, %o0, %o0
			set     	0, %o1
			add     	%g0, %o1, %o1
			add     	%o0, %o1, %o0
			set     	-16, %o1
			add     	%fp, %o1, %o1
			st      	%o0, [%o1]

			! return a3.x;
			set     	-16, %l7
			add     	%fp, %l7, %l7
			ld      	[%l7], %l7
			ld      	[%l7], %i0
			call    	test1.void.fini
			nop
			ret
			restore

			ba      	.$$.endif.1
			nop

		! else
	.$$.else.1:

		! endif
	.$$.endif.1:

		! b3.MYS_B(...)
		set     	-28, %o0
		add     	%fp, %o0, %o0
		! x <- 1
		set     	1, %o1
		call    	MYS_B.MYS_B.int
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.9:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.9, %o0
		set     	-28, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

		! b3.x
		set     	-28, %o0
		add     	%fp, %o0, %o0
		set     	0, %o1
		add     	%g0, %o1, %o1
		add     	%o0, %o1, %o0
		set     	-32, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! (b3.x)==(1)
		set     	-32, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		set     	1, %o1
		cmp     	%o0, %o1
		bne     	.$$.cmp.2
		mov     	%g0, %o0
		inc     	%o0
	.$$.cmp.2:
		set     	-36, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! if ( (b3.x)==(1) )
		set     	-36, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %o0
		cmp     	%o0, %g0
		be      	.$$.else.2
		nop

			! b3.x
			set     	-28, %o0
			add     	%fp, %o0, %o0
			set     	0, %o1
			add     	%g0, %o1, %o1
			add     	%o0, %o1, %o0
			set     	-40, %o1
			add     	%fp, %o1, %o1
			st      	%o0, [%o1]

			! return b3.x;
			set     	-40, %l7
			add     	%fp, %l7, %l7
			ld      	[%l7], %l7
			ld      	[%l7], %i0
			call    	test1.void.fini
			nop
			ret
			restore

			ba      	.$$.endif.2
			nop

		! else
	.$$.else.2:

		! endif
	.$$.endif.2:

		! b4.MYS_B(...)
		set     	-52, %o0
		add     	%fp, %o0, %o0
		! x <- x
		set     	x, %l7
		add     	%g0, %l7, %l7
		ld      	[%l7], %o1
		call    	MYS_B.MYS_B.int
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.10:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.10, %o0
		set     	-52, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

		! b4.f
		set     	-52, %o0
		add     	%fp, %o0, %o0
		set     	4, %o1
		add     	%g0, %o1, %o1
		add     	%o0, %o1, %o0
		set     	-56, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! &b4.f
		set     	-56, %o0
		add     	%fp, %o0, %o0
		ld      	[%o0], %o0
		set     	-60, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! (int*)&b4.f
		set     	-60, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %o0
		set     	-64, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! *(int*)&b4.f
		set     	-64, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %o0
		call    	.$$.ptrCheck
		nop
		set     	-68, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! (*(int*)&b4.f)==(1)
		set     	-68, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		set     	1, %o1
		cmp     	%o0, %o1
		bne     	.$$.cmp.3
		mov     	%g0, %o0
		inc     	%o0
	.$$.cmp.3:
		set     	-72, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! if ( (*(int*)&b4.f)==(1) )
		set     	-72, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %o0
		cmp     	%o0, %g0
		be      	.$$.else.3
		nop

			! exit(1)
			set     	1, %o0
			call    	exit
			nop

			ba      	.$$.endif.3
			nop

		! else
	.$$.else.3:

		! endif
	.$$.endif.3:

		! a4.MYS_A(...)
		set     	-76, %o0
		add     	%fp, %o0, %o0
		call    	MYS_A.MYS_A.void
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.11:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.11, %o0
		set     	-76, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

		! return 10;
		set     	10, %i0
		call    	test1.void.fini
		nop
		ret
		restore

	! End of function test1.void
	call    	test1.void.fini
	nop
	ret
	restore
	SAVE.test1.void = -(92 + 76) & -8

test1.void.fini:
	save    	%sp, -96, %sp
	set     	.$$.ctorDtor.11, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.11.fini.skip
	nop
	call    	MYS_A.$MYS_A.void
	nop
	set     	.$$.ctorDtor.11, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.11.fini.skip:
	set     	.$$.ctorDtor.10, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.10.fini.skip
	nop
	call    	MYS_B.$MYS_B.void
	nop
	set     	.$$.ctorDtor.10, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.10.fini.skip:
	set     	.$$.ctorDtor.9, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.9.fini.skip
	nop
	call    	MYS_B.$MYS_B.void
	nop
	set     	.$$.ctorDtor.9, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.9.fini.skip:
	set     	.$$.ctorDtor.8, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.8.fini.skip
	nop
	call    	MYS_A.$MYS_A.void
	nop
	set     	.$$.ctorDtor.8, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.8.fini.skip:
	ret
	restore
	.global 	main
main:
main.void:
	set     	SAVE.main.void, %g1
	save    	%sp, %g1, %sp

		! Store params

		! a3.MYS_A(...)
		set     	-4, %o0
		add     	%fp, %o0, %o0
		call    	MYS_A.MYS_A.void
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.12:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.12, %o0
		set     	-4, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

		! a3.x
		set     	-4, %o0
		add     	%fp, %o0, %o0
		set     	0, %o1
		add     	%g0, %o1, %o1
		add     	%o0, %o1, %o0
		set     	-8, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! (a3.x)==(1)
		set     	-8, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		set     	1, %o1
		cmp     	%o0, %o1
		bne     	.$$.cmp.4
		mov     	%g0, %o0
		inc     	%o0
	.$$.cmp.4:
		set     	-12, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! if ( (a3.x)==(1) )
		set     	-12, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %o0
		cmp     	%o0, %g0
		be      	.$$.else.4
		nop

			! a3.x
			set     	-4, %o0
			add     	%fp, %o0, %o0
			set     	0, %o1
			add     	%g0, %o1, %o1
			add     	%o0, %o1, %o0
			set     	-16, %o1
			add     	%fp, %o1, %o1
			st      	%o0, [%o1]

			! return a3.x;
			set     	-16, %l7
			add     	%fp, %l7, %l7
			ld      	[%l7], %l7
			ld      	[%l7], %i0
			call    	main.void.fini
			nop
			ret
			restore

			ba      	.$$.endif.4
			nop

		! else
	.$$.else.4:

		! endif
	.$$.endif.4:

		! b3.MYS_B(...)
		set     	-28, %o0
		add     	%fp, %o0, %o0
		! x <- 1
		set     	1, %o1
		call    	MYS_B.MYS_B.int
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.13:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.13, %o0
		set     	-28, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

		! b3.x
		set     	-28, %o0
		add     	%fp, %o0, %o0
		set     	0, %o1
		add     	%g0, %o1, %o1
		add     	%o0, %o1, %o0
		set     	-32, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! (b3.x)==(1)
		set     	-32, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		set     	1, %o1
		cmp     	%o0, %o1
		bne     	.$$.cmp.5
		mov     	%g0, %o0
		inc     	%o0
	.$$.cmp.5:
		set     	-36, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! if ( (b3.x)==(1) )
		set     	-36, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %o0
		cmp     	%o0, %g0
		be      	.$$.else.5
		nop

			! b3.x
			set     	-28, %o0
			add     	%fp, %o0, %o0
			set     	0, %o1
			add     	%g0, %o1, %o1
			add     	%o0, %o1, %o0
			set     	-40, %o1
			add     	%fp, %o1, %o1
			st      	%o0, [%o1]

			! return b3.x;
			set     	-40, %l7
			add     	%fp, %l7, %l7
			ld      	[%l7], %l7
			ld      	[%l7], %i0
			call    	main.void.fini
			nop
			ret
			restore

			ba      	.$$.endif.5
			nop

		! else
	.$$.else.5:

		! endif
	.$$.endif.5:

		! b4.MYS_B(...)
		set     	-52, %o0
		add     	%fp, %o0, %o0
		! x <- x
		set     	x, %l7
		add     	%g0, %l7, %l7
		ld      	[%l7], %o1
		call    	MYS_B.MYS_B.int
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.14:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.14, %o0
		set     	-52, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

		! b4.f
		set     	-52, %o0
		add     	%fp, %o0, %o0
		set     	4, %o1
		add     	%g0, %o1, %o1
		add     	%o0, %o1, %o0
		set     	-56, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! &b4.f
		set     	-56, %o0
		add     	%fp, %o0, %o0
		ld      	[%o0], %o0
		set     	-60, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! (int*)&b4.f
		set     	-60, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %o0
		set     	-64, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! *(int*)&b4.f
		set     	-64, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %o0
		call    	.$$.ptrCheck
		nop
		set     	-68, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! (*(int*)&b4.f)==(1)
		set     	-68, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %l7
		ld      	[%l7], %o0
		set     	1, %o1
		cmp     	%o0, %o1
		bne     	.$$.cmp.6
		mov     	%g0, %o0
		inc     	%o0
	.$$.cmp.6:
		set     	-72, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! if ( (*(int*)&b4.f)==(1) )
		set     	-72, %l7
		add     	%fp, %l7, %l7
		ld      	[%l7], %o0
		cmp     	%o0, %g0
		be      	.$$.else.6
		nop

			! exit(1)
			set     	1, %o0
			call    	exit
			nop

			ba      	.$$.endif.6
			nop

		! else
	.$$.else.6:

		! endif
	.$$.endif.6:

		! a4.MYS_A(...)
		set     	-76, %o0
		add     	%fp, %o0, %o0
		call    	MYS_A.MYS_A.void
		nop

		.section	".bss"
		.align  	4
	.$$.ctorDtor.15:
		.skip   	4

		.section	".text"
		.align  	4

		set     	.$$.ctorDtor.15, %o0
		set     	-76, %o1
		add     	%fp, %o1, %o1
		st      	%o1, [%o0]

		! test1(...)
		call    	test1.void
		nop
		set     	-80, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! test1(...)
		call    	test1.void
		nop
		set     	-84, %o1
		add     	%fp, %o1, %o1
		st      	%o0, [%o1]

		! return 10;
		set     	10, %i0
		call    	main.void.fini
		nop
		ret
		restore

	! End of function main.void
	call    	main.void.fini
	nop
	ret
	restore
	SAVE.main.void = -(92 + 84) & -8

main.void.fini:
	save    	%sp, -96, %sp
	set     	.$$.ctorDtor.15, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.15.fini.skip
	nop
	call    	MYS_A.$MYS_A.void
	nop
	set     	.$$.ctorDtor.15, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.15.fini.skip:
	set     	.$$.ctorDtor.14, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.14.fini.skip
	nop
	call    	MYS_B.$MYS_B.void
	nop
	set     	.$$.ctorDtor.14, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.14.fini.skip:
	set     	.$$.ctorDtor.13, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.13.fini.skip
	nop
	call    	MYS_B.$MYS_B.void
	nop
	set     	.$$.ctorDtor.13, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.13.fini.skip:
	set     	.$$.ctorDtor.12, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.12.fini.skip
	nop
	call    	MYS_A.$MYS_A.void
	nop
	set     	.$$.ctorDtor.12, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.12.fini.skip:
	ret
	restore
	.global 	something
something:
something.void:
	set     	SAVE.something.void, %g1
	save    	%sp, %g1, %sp

		! Store params

	! End of function something.void
	call    	something.void.fini
	nop
	ret
	restore
	SAVE.something.void = -(92 + 0) & -8

something.void.fini:
	save    	%sp, -96, %sp
	ret
	restore
.$$.ctorDtor.7.fini:
	save    	%sp, -96, %sp
	set     	.$$.ctorDtor.7, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.7.fini.skip
	nop
	call    	MYS_A.$MYS_A.void
	nop
	set     	.$$.ctorDtor.7, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.7.fini.skip:
	ret
	restore

	.section	".fini"
	.align  	4
	call    	.$$.ctorDtor.7.fini
	nop

	.section	".text"
	.align  	4
.$$.ctorDtor.6.fini:
	save    	%sp, -96, %sp
	set     	.$$.ctorDtor.6, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.6.fini.skip
	nop
	call    	MYS_B.$MYS_B.void
	nop
	set     	.$$.ctorDtor.6, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.6.fini.skip:
	ret
	restore

	.section	".fini"
	.align  	4
	call    	.$$.ctorDtor.6.fini
	nop

	.section	".text"
	.align  	4
.$$.ctorDtor.5.fini:
	save    	%sp, -96, %sp
	set     	.$$.ctorDtor.5, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.5.fini.skip
	nop
	call    	MYS_B.$MYS_B.void
	nop
	set     	.$$.ctorDtor.5, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.5.fini.skip:
	ret
	restore

	.section	".fini"
	.align  	4
	call    	.$$.ctorDtor.5.fini
	nop

	.section	".text"
	.align  	4
.$$.ctorDtor.4.fini:
	save    	%sp, -96, %sp
	set     	.$$.ctorDtor.4, %o0
	ld      	[%o0], %o0
	cmp     	%o0, %g0
	be      	.$$.ctorDtor.4.fini.skip
	nop
	call    	MYS_A.$MYS_A.void
	nop
	set     	.$$.ctorDtor.4, %o0
	st      	%g0, [%o0]
.$$.ctorDtor.4.fini.skip:
	ret
	restore

	.section	".fini"
	.align  	4
	call    	.$$.ctorDtor.4.fini
	nop

	.section	".text"
	.align  	4
