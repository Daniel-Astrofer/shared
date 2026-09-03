# Agent guide — Kerosene Shared

## Scope

This repository owns stable Java runtime utilities shared by Auth and KFE. It
does not own service business rules, financial behavior, deployment or protocol
definitions.

## Documentation

- Start at `docs/README.md`.
- Put library facts in `docs/reference/`; retain state and setup in `STATUS.md`
  and `QUICKSTART.md`.
- Link to Contracts and the owning service instead of duplicating their rules.

## Safety and integration

- Never add credentials, deployment manifests or cross-repository protocol
  implementations.
- Keep the public utility surface backward compatible and documented.

## Verification

Run the relevant Gradle verification and update the API catalog with any public
surface change.
