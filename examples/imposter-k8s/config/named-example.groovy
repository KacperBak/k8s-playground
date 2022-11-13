
// Example of returning a specific example, based on the request URI.

if (context.request.pathParams.petId == '1') {
    respond().withExampleName('catExample')

} else if (context.request.pathParams.petId == '2') {
    respond().withExampleName('dogExample')
} else if (context.request.pathParams.petId == '3') {
    respond()
    .withStatusCode(200)
    .withFile('tortoise.json')
    .withDelayRange(500, 1500)
} else {
    respond()
    .withStatusCode(404)
    .withData('')
}
